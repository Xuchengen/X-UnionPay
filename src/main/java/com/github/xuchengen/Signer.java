package com.github.xuchengen;

import java.util.Map;

/**
 * 签名器
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/9/2
 */
public interface Signer {

    /**
     * 摘要
     *
     * @param paramStr 参数字符串
     * @param charset  字符编码
     * @return 摘要字符串
     */
    String digest(String paramStr, String charset);

    /**
     * 将字符串参数进行签名
     *
     * @param paramStr 待签名字符串
     * @return 签名字符串
     */
    String sign(String paramStr);

    /**
     * 验证签名
     *
     * @param paramMap Map参数
     * @param charset  字符编码
     * @return boolean
     */
    boolean verify(Map<String, String> paramMap, String charset);

    /**
     * 获取证书ID号
     *
     * @return 证书ID号
     */
    String getCertId();

    /**
     * 获取版本号
     *
     * @return 版本号
     */
    String getVersion();

    /**
     * 获取签名方式
     *
     * @return 签名方式
     */
    String getSignMethod();

    /**
     * 获取加密证书字符串
     *
     * @return 加密证书字符串
     */
    String getEncryptCertStr();

    /**
     * 获取商户私钥证书字符串
     *
     * @return 商户私钥证书字符串
     */
    String getMerchantPrivateKeyStr();
}
