package com.github.xuchengen.request;


import com.github.xuchengen.UnionPayRequest;
import com.github.xuchengen.response.UnionPayEncryPubKeyQueryResponse;

/**
 * 在线网关支付--加密公钥更新查询接口请求模型
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/8/30
 */
public class UnionPayEncryPubKeyQueryRequest extends UnionPayCommonRequest implements UnionPayRequest<UnionPayEncryPubKeyQueryResponse> {

    private static final String API_PATH = "/gateway/api/backTransReq.do";

    /**
     * 产品类型
     */
    private String bizType = "000000";

    /**
     * 渠道类型
     */
    private String channelType;

    /**
     * 商户订单号
     */
    private String orderId;

    /**
     * 证书类型
     */
    private String certType;

    /**
     * 二级商户代码
     */
    private String subMerId;

    /**
     * 二级商户简称
     */
    private String subMerAbbr;

    /**
     * 二级商户名称
     */
    private String subMerName;

    /**
     * 商户简称
     */
    private String merAbbr;

    /**
     * 商户类别
     */
    private String merCatCode;

    /**
     * 商户名称
     */
    private String merName;

    /**
     * 收单机构代码
     */
    private String acqInsCode;

    /**
     * 保留域
     */
    private String reserved;

    /**
     * 请求方保留域
     */
    private String reqReserved;

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getSubMerId() {
        return subMerId;
    }

    public void setSubMerId(String subMerId) {
        this.subMerId = subMerId;
    }

    public String getSubMerAbbr() {
        return subMerAbbr;
    }

    public void setSubMerAbbr(String subMerAbbr) {
        this.subMerAbbr = subMerAbbr;
    }

    public String getSubMerName() {
        return subMerName;
    }

    public void setSubMerName(String subMerName) {
        this.subMerName = subMerName;
    }

    public String getMerAbbr() {
        return merAbbr;
    }

    public void setMerAbbr(String merAbbr) {
        this.merAbbr = merAbbr;
    }

    public String getMerCatCode() {
        return merCatCode;
    }

    public void setMerCatCode(String merCatCode) {
        this.merCatCode = merCatCode;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public String getAcqInsCode() {
        return acqInsCode;
    }

    public void setAcqInsCode(String acqInsCode) {
        this.acqInsCode = acqInsCode;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public String getReqReserved() {
        return reqReserved;
    }

    public void setReqReserved(String reqReserved) {
        this.reqReserved = reqReserved;
    }

    @Override
    public String getApiPath() {
        return API_PATH;
    }

    @Override
    public Class<UnionPayEncryPubKeyQueryResponse> getResponseClass() {
        return UnionPayEncryPubKeyQueryResponse.class;
    }
}
