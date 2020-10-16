package com.github.xuchengen.helper;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.CryptoException;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.github.xuchengen.UnionPayCertInfo;
import com.github.xuchengen.UnionPayConstants;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * 银联支付工具类
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/8/29
 */
public class UnionPayHelper {

    private static final String PKCS12 = "PKCS12";

    private static final String X509 = "X.509";

    private static final String BEGIN_CERTIFICATE = "-----BEGIN CERTIFICATE-----";

    private static final String END_CERTIFICATE = "-----END CERTIFICATE-----";

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * 构建提交表单
     *
     * @param apiUrl   接口地址
     * @param hiddens  隐藏域参数
     * @param encoding 字符编码
     * @return html表单字符串
     */
    public static String buildSubmitFormStr(String apiUrl, Map<String, String> hiddens, String encoding) {
        StringBuffer sf = new StringBuffer();
        sf.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=" + encoding + "\"/></head><body>");
        sf.append("<form id = \"unionpay_form\" action=\"" + apiUrl
                + "\" method=\"post\">");
        if (null != hiddens && 0 != hiddens.size()) {
            Set<Map.Entry<String, String>> set = hiddens.entrySet();
            Iterator<Map.Entry<String, String>> it = set.iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> ey = it.next();
                String key = ey.getKey();
                String value = ey.getValue();
                sf.append("<input type=\"hidden\" name=\"" + key + "\" id=\""
                        + key + "\" value=\"" + value + "\"/>");
            }
        }
        sf.append("</form>");
        sf.append("</body>");
        sf.append("<script type=\"text/javascript\">");
        sf.append("document.all.unionpay_form.submit();");
        sf.append("</script>");
        sf.append("</html>");
        return sf.toString();
    }

    /**
     * 构建键值对参数字符串
     *
     * @param data Map参数
     * @return 字符串
     */
    public static String buildKVPairStr(Map<String, String> data) {
        TreeMap<String, String> tree = new TreeMap<>();
        Iterator<Map.Entry<String, String>> iter = data.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            if (UnionPayConstants.VAR_SIGNATURE.equals(entry.getKey().trim())) {
                continue;
            }
            tree.put(entry.getKey(), entry.getValue());
        }
        iter = tree.entrySet().iterator();
        StringBuilder stringBuilder = new StringBuilder();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            stringBuilder.append(entry.getKey())
                    .append(UnionPayConstants.EQUAL)
                    .append(entry.getValue())
                    .append(UnionPayConstants.AMPERSAND);
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    /**
     * 构建键值对参数字符串使用URLEncode编码value值
     *
     * @param paramMap Map参数
     * @param charset  字符编码
     * @return 字符串
     */
    public static String buildKVPairStrWithURLEncode(Map<String, String> paramMap, String charset) {
        StringBuffer sf = new StringBuffer();
        String reqstr = "";
        if (CollUtil.isNotEmpty(paramMap)) {
            for (Map.Entry<String, String> en : paramMap.entrySet()) {
                sf.append(en.getKey())
                        .append(UnionPayConstants.EQUAL)
                        .append(StrUtil.isBlank(en.getValue()) ? "" : URLUtil.encodeQuery(en.getValue(), charset))
                        .append(UnionPayConstants.AMPERSAND);
            }
            reqstr = sf.substring(0, sf.length() - 1);
        }
        return reqstr;
    }

    /**
     * 根据键值对参数字符串构建Map
     *
     * @param result KV键值对参数字符串
     * @return Map
     */
    public static Map<String, String> buildMapByKVPairStr(String result) {
        Map<String, String> map = null;
        if (StrUtil.isNotBlank(result)) {
            if (result.startsWith("{") && result.endsWith("}")) {
                result = result.substring(1, result.length() - 1);
            }
            map = parseQString(result);
        }
        return map;
    }

    /**
     * 解析应答字符串，生成应答要素
     *
     * @param str 需要解析的字符串
     * @return 解析的结果map
     */
    private static Map<String, String> parseQString(String str) {
        Map<String, String> map = new TreeMap<>();
        int len = str.length();
        StringBuilder temp = new StringBuilder();
        char curChar;
        String key = null;
        boolean isKey = true;
        boolean isOpen = false;
        char openName = 0;
        if (len > 0) {
            for (int i = 0; i < len; i++) {
                curChar = str.charAt(i);
                if (isKey) {
                    if (curChar == '=') {
                        key = temp.toString();
                        temp.setLength(0);
                        isKey = false;
                    } else {
                        temp.append(curChar);
                    }
                } else {
                    if (isOpen) {
                        if (curChar == openName) {
                            isOpen = false;
                        }
                    } else {
                        if (curChar == '{') {
                            isOpen = true;
                            openName = '}';
                        }
                        if (curChar == '[') {
                            isOpen = true;
                            openName = ']';
                        }
                    }
                    if (curChar == '&' && !isOpen) {
                        putKeyValueToMap(temp, isKey, key, map);
                        temp.setLength(0);
                        isKey = true;
                    } else {
                        temp.append(curChar);
                    }
                }
            }
            putKeyValueToMap(temp, isKey, key, map);
        }
        return map;
    }

    /**
     * 设置键值到Map中
     *
     * @param temp  StringBuilder
     * @param isKey 是否为键
     * @param key   键
     * @param map   原Map
     */
    private static void putKeyValueToMap(StringBuilder temp, boolean isKey, String key, Map<String, String> map) {
        if (isKey) {
            key = temp.toString();
            if (key.length() == 0) {
                throw new RuntimeException("QString format illegal");
            }
            map.put(key, "");
        } else {
            if (key.length() == 0) {
                throw new RuntimeException("QString format illegal");
            }
            map.put(key, temp.toString());
        }
    }

    /**
     * 从输入流获取密钥信息
     *
     * @param inputStream 输入流
     * @param password    密码
     * @return UnionpayCertInfo
     */
    public static UnionPayCertInfo getCertInfoFromInputStream(InputStream inputStream, String password) {
        try {
            char[] passwordCharArray = password.toCharArray();
            KeyStore keyStore = KeyUtil.readKeyStore(PKCS12, inputStream, passwordCharArray);
            Enumeration enumas = keyStore.aliases();
            String keyAlias = null;
            if (enumas.hasMoreElements()) {
                keyAlias = (String) enumas.nextElement();
            }
            KeyPair keyPair = KeyUtil.getKeyPair(keyStore, passwordCharArray, keyAlias);
            X509Certificate certificate = (X509Certificate) keyStore.getCertificate(keyAlias);

            return new UnionPayCertInfo()
                    .setKeyPair(keyPair)
                    .setSerialNo(certificate.getSerialNumber().toString())
                    .setPublicKeyStr(Base64.encode(keyPair.getPublic().getEncoded()))
                    .setPrivateKeyStr(Base64.encode(keyPair.getPrivate().getEncoded()));
        } catch (Exception e) {
            throw new CryptoException(e);
        } finally {
            IoUtil.close(inputStream);
        }
    }

    /**
     * 获取证书的CN
     *
     * @param certificate 证书
     * @return 字符串
     */
    public static String getIdentitiesFromCertficate(X509Certificate certificate) {
        String tDN = certificate.getSubjectDN().toString();
        String tPart = "";
        if ((tDN != null)) {
            String tSplitStr[] = tDN.substring(tDN.indexOf("CN=")).split("@");
            if (tSplitStr != null && tSplitStr.length > 2
                    && tSplitStr[2] != null) {
                tPart = tSplitStr[2];
            }
        }
        return tPart;
    }

    /**
     * 从输入流中读取证书字符串
     *
     * @param inputStream 输入流
     * @return 字符串
     */
    public static String getCertStrFromInputStream(InputStream inputStream) {
        try {
            Certificate certificate = KeyUtil.readCertificate(X509, inputStream);

            return BEGIN_CERTIFICATE +
                    LINE_SEPARATOR +
                    Base64.encode(certificate.getEncoded()) +
                    LINE_SEPARATOR +
                    END_CERTIFICATE;
        } catch (Exception e) {
            throw new CryptoException(e);
        } finally {
            IoUtil.close(inputStream);
        }
    }

    /**
     * 从证书字符串获取证书
     *
     * @param certStr     证书字符串
     * @param isFormatter 是否格式化
     * @return X509证书
     */
    public static X509Certificate getCertFromCertStr(String certStr, boolean isFormatter) {
        if (isFormatter) {
            certStr = BEGIN_CERTIFICATE +
                    LINE_SEPARATOR +
                    certStr +
                    LINE_SEPARATOR +
                    END_CERTIFICATE;
        }
        ByteArrayInputStream byteArrayInputStream = IoUtil.toUtf8Stream(certStr);
        Certificate certificate = KeyUtil.readX509Certificate(byteArrayInputStream);
        IoUtil.close(byteArrayInputStream);
        return (X509Certificate) certificate;
    }

    /**
     * 获取SHA1摘要字符串
     *
     * @param paramStr 参数字符串
     * @param charset  字符编码
     * @return 字符串
     */
    public static String getSHA1Digest(String paramStr, String charset) {
        return new Digester(DigestAlgorithm.SHA1).digestHex(paramStr, charset);
    }

    /**
     * 获取SHA256摘要字符串
     *
     * @param paramStr 参数字符串
     * @param charset  字符编码
     * @return 字符串
     */
    public static String getSHA256Digest(String paramStr, String charset) {
        return new Digester(DigestAlgorithm.SHA256).digestHex(paramStr, charset);
    }

    /**
     * 通过SHA1withRSA算法进行签名
     *
     * @param paramStr   参数字符串
     * @param privateKey 私钥
     * @return 字符串
     */
    public static String signBySHA1withRSA(String paramStr, String privateKey) {
        Sign sign = SecureUtil.sign(SignAlgorithm.SHA1withRSA, privateKey, null);
        return Base64.encode(sign.sign(StrUtil.bytes(paramStr)));
    }

    /**
     * 通过SHA256withRSA算法进行签名
     *
     * @param paramStr   参数字符串
     * @param privateKey 私钥
     * @return 字符串
     */
    public static String signBySHA256withRSA(String paramStr, String privateKey) {
        Sign sign = SecureUtil.sign(SignAlgorithm.SHA256withRSA, privateKey, null);
        return Base64.encode(sign.sign(StrUtil.bytes(paramStr)));
    }

    /**
     * 验证签名
     *
     * @param digestData 摘要数据
     * @param signData   签名数据
     * @param publicKey  公钥
     * @return 验证结果
     */
    public static boolean verifyBySHA256withRSA(byte[] digestData, byte[] signData, byte[] publicKey) {
        Sign _sign = SecureUtil.sign(SignAlgorithm.SHA256withRSA, null, publicKey);
        return _sign.verify(digestData, signData);
    }

    /**
     * 验证签名
     *
     * @param digestData 摘要数据
     * @param signData   签名数据
     * @param publicKey  公钥
     * @return 验证结果
     */
    public static boolean verifyBySHA1withRSA(byte[] digestData, byte[] signData, byte[] publicKey) {
        Sign _sign = SecureUtil.sign(SignAlgorithm.SHA1withRSA, null, publicKey);
        return _sign.verify(digestData, signData);
    }
}
