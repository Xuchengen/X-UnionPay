package com.github.xuchengen.request;

/**
 * 公共请求参数模型
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019-08-27
 */
public class UnionPayCommonRequest {

    /**
     * 交易类型
     */
    private String txnType;

    /**
     * 交易子类
     */
    private String txnSubType;

    /**
     * 接入类型
     */
    private String accessType;

    public String getTxnType() {
        return txnType;
    }

    public UnionPayCommonRequest setTxnType(String txnType) {
        this.txnType = txnType;
        return this;
    }

    public String getTxnSubType() {
        return txnSubType;
    }

    public UnionPayCommonRequest setTxnSubType(String txnSubType) {
        this.txnSubType = txnSubType;
        return this;
    }

    public String getAccessType() {
        return accessType;
    }

    public UnionPayCommonRequest setAccessType(String accessType) {
        this.accessType = accessType;
        return this;
    }
}
