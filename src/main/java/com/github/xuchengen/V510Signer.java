package com.github.xuchengen;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.github.xuchengen.helper.UnionPayHelper;

import java.security.cert.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 银联在线网关支付5.1.0版本签名器
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/9/2
 */
public class V510Signer implements Signer {

    private static final Log log = LogFactory.get(UnionPayConstants.UNIONPAY_LOG);

    private static final String VERSION = UnionPayConstants.V510_VERSION;

    private static final String SIGN_METHOD = UnionPayConstants.SIGN_METHOD_RSA;

    /**
     * 加密证书
     */
    private String encryCertStr;

    /**
     * 根证书
     */
    private String rootCertStr;

    /**
     * 中间证书
     */
    private String middleCertStr;

    /**
     * 商户证书序列号
     */
    private String serialNo;

    /**
     * 商户私钥证书
     */
    private String merchantPrivateKey;

    /**
     * 验证证书名称
     */
    private boolean verifyCNName;

    public String getEncryCertStr() {
        return encryCertStr;
    }

    public V510Signer setEncryCertStr(String encryCertStr) {
        this.encryCertStr = encryCertStr;
        return this;
    }

    public String getRootCertStr() {
        return rootCertStr;
    }

    public V510Signer setRootCertStr(String rootCertStr) {
        this.rootCertStr = rootCertStr;
        return this;
    }

    public String getMiddleCertStr() {
        return middleCertStr;
    }

    public V510Signer setMiddleCertStr(String middleCertStr) {
        this.middleCertStr = middleCertStr;
        return this;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public V510Signer setSerialNo(String serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    public String getMerchantPrivateKey() {
        return merchantPrivateKey;
    }

    public V510Signer setMerchantPrivateKey(String merchantPrivateKey) {
        this.merchantPrivateKey = merchantPrivateKey;
        return this;
    }

    public boolean isVerifyCNName() {
        return verifyCNName;
    }

    public V510Signer setVerifyCNName(boolean verifyCNName) {
        this.verifyCNName = verifyCNName;
        return this;
    }

    @Override
    public String digest(String paramStr, String charset) {
        return UnionPayHelper.getSHA256Digest(paramStr, charset);
    }

    @Override
    public String sign(String paramStr) {
        return UnionPayHelper.signBySHA256withRSA(paramStr, merchantPrivateKey);
    }

    @Override
    public boolean verify(Map<String, String> paramMap, String charset) {
        if (!(paramMap instanceof TreeMap)) {
            paramMap = new TreeMap<>(paramMap);
        }

        if (log.isDebugEnabled()) {
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                log.debug("[{}]<=====>[{}]", entry.getKey(), entry.getValue());
            }
        }

        String signStr = "";
        if (paramMap.containsKey(UnionPayConstants.VAR_SIGNATURE)) {
            signStr = paramMap.remove(UnionPayConstants.VAR_SIGNATURE);
        }

        String signPublicKeyCert = paramMap.get(UnionPayConstants.VAR_SIGN_PUBLIC_KEY_CERT);

        if (StrUtil.isBlank(signPublicKeyCert)) {
            log.warn("签名公钥证书为空");
            return false;
        }

        X509Certificate signCert = UnionPayHelper.getCertFromCertStr(signPublicKeyCert, false);
        X509Certificate middleCert = UnionPayHelper.getCertFromCertStr(middleCertStr, true);
        X509Certificate rootCert = UnionPayHelper.getCertFromCertStr(rootCertStr, true);

        try {
            signCert.checkValidity();
        } catch (CertificateExpiredException e) {
            log.warn("签名公钥证书已过期：[{}]", signPublicKeyCert, e);
            return false;
        } catch (CertificateNotYetValidException e) {
            log.warn("签名公钥证书尚未生效：[{}]", signPublicKeyCert, e);
            return false;
        }

        try {
            X509CertSelector selector = new X509CertSelector();
            selector.setCertificate(signCert);

            Set<TrustAnchor> trustAnchors = new HashSet<>();
            trustAnchors.add(new TrustAnchor(rootCert, null));
            PKIXBuilderParameters pkixParams = new PKIXBuilderParameters(
                    trustAnchors, selector);

            Set<X509Certificate> intermediateCerts = new HashSet<>();
            intermediateCerts.add(rootCert);
            intermediateCerts.add(middleCert);
            intermediateCerts.add(signCert);

            pkixParams.setRevocationEnabled(false);

            CertStore intermediateCertStore = CertStore.getInstance("Collection",
                    new CollectionCertStoreParameters(intermediateCerts));
            pkixParams.addCertStore(intermediateCertStore);

            CertPathBuilder builder = CertPathBuilder.getInstance("PKIX");

            builder.build(pkixParams);

        } catch (java.security.cert.CertPathBuilderException e) {
            log.warn("验证证书链失败", e);
            return false;
        } catch (Exception e) {
            log.warn("验证证书链异常", e);
            return false;
        }

        String identitiesFromCertficate = UnionPayHelper.getIdentitiesFromCertficate(signCert);
        if (verifyCNName) {
            // 验证公钥是否属于银联
            if (!UnionPayConstants.UNIONPAY_CNNAME.equals(identitiesFromCertficate)) {
                log.warn("该证书所有者不是中国银联:[{}]", identitiesFromCertficate);
                return false;
            }
        } else {
            // 验证公钥是否属于银联
            if (!UnionPayConstants.UNIONPAY_CNNAME.equals(identitiesFromCertficate)
                    && !"00040000:SIGN".equals(identitiesFromCertficate)) {
                log.warn("该证书所有者不是中国银联:[{}]", identitiesFromCertficate);
                return false;
            }
        }

        String kvPairStr = UnionPayHelper.buildKVPairStr(paramMap);

        String digest = UnionPayHelper.getSHA256Digest(kvPairStr, charset);

        byte[] publicKey = signCert.getPublicKey().getEncoded();

        byte[] signData = Base64.decode(signStr);

        byte[] digestData = StrUtil.bytes(digest);

        boolean result = UnionPayHelper.verifyBySHA256withRSA(digestData, signData, publicKey);

        if (log.isDebugEnabled()) {
            log.debug("银联签名字符串：[{}]", signStr);

            log.debug("待验签键值对字符串：[{}]", kvPairStr);

            log.debug("待验签摘要字符串：[{}]", digest);

            log.debug("验签结果：[{}]", result);
        }

        return result;
    }

    @Override
    public String getCertId() {
        return serialNo;
    }

    @Override
    public String getVersion() {
        return VERSION;
    }

    @Override
    public String getSignMethod() {
        return SIGN_METHOD;
    }

    @Override
    public String getEncryptCertStr() {
        return encryCertStr;
    }

    @Override
    public String getMerchantPrivateKeyStr() {
        return merchantPrivateKey;
    }
}
