package com.github.xuchengen;

import cn.hutool.core.util.StrUtil;

import java.io.Serializable;

/**
 * 响应
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/9/2
 */
public abstract class UnionPayResponse implements Serializable {

    private static final long serialVersionUID = 6526957735916646429L;

    /**
     * 应答码
     */
    private String respCode;

    /**
     * 应答信息
     */
    private String respMsg;

    /**
     * 响应body
     */
    private String body;

    /**
     * 签名
     */
    private String signature;

    /**
     * 签名公钥证书
     */
    private String signPubKeyCert;

    /**
     * 编码方式
     */
    private String encoding;

    /**
     * 版本
     */
    private String version;

    /**
     * 签名方法
     */
    private String signMethod;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSignPubKeyCert() {
        return signPubKeyCert;
    }

    public void setSignPubKeyCert(String signPubKeyCert) {
        this.signPubKeyCert = signPubKeyCert;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSignMethod() {
        return signMethod;
    }

    public void setSignMethod(String signMethod) {
        this.signMethod = signMethod;
    }

    public boolean isOk() {
        return StrUtil.isBlank(respCode) || "00".equals(respCode);
    }
}
