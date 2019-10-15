package com.github.xuchengen;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.github.xuchengen.helper.UnionPayHelper;

import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.TreeMap;

/**
 * 银联在线网关支付5.0.0版本签名器
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/9/27
 */
public class V500Signer implements Signer {

    private static final Log log = LogFactory.get(UnionPayConstants.UNIONPAY_LOG);

    private static final String VERSION = UnionPayConstants.V500_VERSION;

    private static final String SIGN_METHOD = UnionPayConstants.SIGN_METHOD_RSA;

    /**
     * 加密证书
     */
    private String encryCertStr;

    /**
     * 商户证书序列号
     */
    private String serialNo;

    /**
     * 商户私钥证书
     */
    private String merchantPrivateKey;

    /**
     * 银联公钥证书
     */
    private String unionPayPublicKey;

    public String getEncryCertStr() {
        return encryCertStr;
    }

    public V500Signer setEncryCertStr(String encryCertStr) {
        this.encryCertStr = encryCertStr;
        return this;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public V500Signer setSerialNo(String serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    public String getMerchantPrivateKey() {
        return merchantPrivateKey;
    }

    public V500Signer setMerchantPrivateKey(String merchantPrivateKey) {
        this.merchantPrivateKey = merchantPrivateKey;
        return this;
    }

    public String getUnionPayPublicKey() {
        return unionPayPublicKey;
    }

    public V500Signer setUnionPayPublicKey(String unionPayPublicKey) {
        this.unionPayPublicKey = unionPayPublicKey;
        return this;
    }

    @Override
    public String digest(String paramStr, String charset) {
        return UnionPayHelper.getSHA1Digest(paramStr, charset);
    }

    @Override
    public String sign(String paramStr) {
        return UnionPayHelper.signBySHA1withRSA(paramStr, merchantPrivateKey);
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

        String kvPairStr = UnionPayHelper.buildKVPairStr(paramMap);

        String digest = UnionPayHelper.getSHA1Digest(kvPairStr, charset);

        X509Certificate unionPayPublicCert = UnionPayHelper.getCertFromCertStr(unionPayPublicKey, true);

        byte[] publicKey = unionPayPublicCert.getPublicKey().getEncoded();

        byte[] signData = Base64.decode(signStr);

        byte[] digestData = StrUtil.bytes(digest);

        boolean result = UnionPayHelper.verifyBySHA1withRSA(digestData, signData, publicKey);

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
