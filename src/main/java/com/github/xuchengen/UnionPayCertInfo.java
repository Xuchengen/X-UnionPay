package com.github.xuchengen;

import java.security.KeyPair;

/**
 * 银联证书信息
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/8/28
 */
public class UnionPayCertInfo {

    /**
     * 密钥对
     */
    private KeyPair keyPair;

    /**
     * 证书序列号
     */
    private String serialNo;

    /**
     * 公钥字符串
     */
    private String publicKeyStr;

    /**
     * 私钥字符串
     */
    private String privateKeyStr;

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public UnionPayCertInfo setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
        return this;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public UnionPayCertInfo setSerialNo(String serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    public String getPublicKeyStr() {
        return publicKeyStr;
    }

    public UnionPayCertInfo setPublicKeyStr(String publicKeyStr) {
        this.publicKeyStr = publicKeyStr;
        return this;
    }

    public String getPrivateKeyStr() {
        return privateKeyStr;
    }

    public UnionPayCertInfo setPrivateKeyStr(String privateKeyStr) {
        this.privateKeyStr = privateKeyStr;
        return this;
    }

    @Override
    public String toString() {
        return "UnionpayCertInfo{" +
                "keyPair=" + keyPair +
                ", serialNo='" + serialNo + '\'' +
                ", publicKeyStr='" + publicKeyStr + '\'' +
                ", privateKeyStr='" + privateKeyStr + '\'' +
                '}';
    }
}
