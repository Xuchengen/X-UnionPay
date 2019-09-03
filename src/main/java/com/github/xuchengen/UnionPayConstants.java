package com.github.xuchengen;

/**
 * 银联常量
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019-08-27
 */
public interface UnionPayConstants {

    /**
     * 银联日志记录标识
     */
    String UNIONPAY_LOG = "UnionpayLogger";

    /**
     * 中国银联证书CNNAME
     */
    String UNIONPAY_CNNAME = "中国银联股份有限公司";

    /**
     * 5.1.0版本
     */
    String V510_VERSION = "5.1.0";

    /**
     * 5.0.0版本
     */
    String V500_VERSION = "5.0.0";

    /**
     * AND符号
     */
    String AMPERSAND = "&";

    /**
     * EQ符号
     */
    String EQUAL = "=";

    /**
     * UTF-8字符集
     */
    String DEFAULT_UTF8 = "UTF-8";

    /**
     * 签名方法
     */
    String VAR_SIGN_METHOD = "signMethod";

    /**
     * 商户ID
     */
    String VAR_MERCHANT_ID = "merId";

    /**
     * 证书ID
     */
    String VAR_CERT_ID = "certId";

    /**
     * 版本号
     */
    String VAR_VERSION = "version";

    /**
     * 编码方式
     */
    String VAR_ENCODING = "encoding";

    /**
     * 签名
     */
    String VAR_SIGNATURE = "signature";

    /**
     * 交易时间
     */
    String VAR_TXN_TIME = "txnTime";

    /**
     * 签名公钥证书
     */
    String VAR_SIGN_PUBLIC_KEY_CERT = "signPubKeyCert";

    /**
     * RSA签名
     */
    String SIGN_METHOD_RSA = "01";

    /**
     * 支持散列方式验证SHA-256
     */
    String SIGN_METHOD_SHA_256 = "11";

    /**
     * 支持散列方式验证SM3
     */
    String SIGN_METHOD_SM3 = "12";

    /**
     * 默认时间格式
     */
    String DEFAULT_DATE_TIME_FORMAT = "yyyyMMddHHmmss";

    /**
     * 日期格式MMdd
     */
    String DATE_MM_DD = "yyyyMMdd";
}
