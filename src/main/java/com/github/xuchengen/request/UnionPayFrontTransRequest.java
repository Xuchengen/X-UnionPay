package com.github.xuchengen.request;


import com.github.xuchengen.UnionPayRequest;
import com.github.xuchengen.response.UnionPayFrontTransResponse;

/**
 * 在线网关支付——消费接口请求模型
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019-08-26
 */
public class UnionPayFrontTransRequest extends UnionPayCommonRequest implements UnionPayRequest<UnionPayFrontTransResponse> {

    /**
     * 接口路径
     */
    private static final String API_PATH = "/gateway/api/frontTransReq.do";

    /**
     * 产品类型
     */
    private String bizType = "000201";

    /**
     * 后台通知地址
     */
    private String backUrl;

    /**
     * 前台通知地址
     */
    private String frontUrl;

    /**
     * 交易币种
     */
    private String currencyCode;

    /**
     * 交易金额
     */
    private String txnAmt;

    /**
     * 渠道类型
     */
    private String channelType;

    /**
     * 商户订单号
     */
    private String orderId;

    /**
     * 订单描述
     */
    private String orderDesc;

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
     * 发卡机构代码
     */
    private String issInsCode;

    /**
     * 分期付款信息域
     */
    private String instalTransInfo;

    /**
     * 银行卡验证信息及身份信息
     */
    private String customerInfo;

    /**
     * 有卡交易信息域
     */
    private String cardTransData;

    /**
     * 预付卡通道
     */
    private String accountPayChannel;

    /**
     * 账号
     */
    private String accNo;

    /**
     * 账号类型(卡介质)
     */
    private String accType;

    /**
     * 保留域
     */
    private String reserved;

    /**
     * 持卡人IP
     */
    private String customerIp;

    /**
     * 订单接收超时时间
     */
    private String orderTimeout;

    /**
     * 分账域
     */
    private String accSplitData;

    /**
     * 风控信息域
     */
    private String riskRateInfo;

    /**
     * 控制规则
     */
    private String ctrlRule;

    /**
     * 默认支付方式
     */
    private String defaultPayType;

    /**
     * 请求方保留域
     */
    private String reqReserved;

    /**
     * 失败交易前台跳转地址
     */
    private String frontFailUrl;

    /**
     * 支持支付方式
     */
    private String supPayType;

    /**
     * 支付超时时间
     */
    private String payTimeout;

    /**
     * 终端号
     */
    private String termId;

    /**
     * 终端信息域
     */
    private String userMac;

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    public String getFrontUrl() {
        return frontUrl;
    }

    public void setFrontUrl(String frontUrl) {
        this.frontUrl = frontUrl;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
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

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
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

    public String getIssInsCode() {
        return issInsCode;
    }

    public void setIssInsCode(String issInsCode) {
        this.issInsCode = issInsCode;
    }

    public String getInstalTransInfo() {
        return instalTransInfo;
    }

    public void setInstalTransInfo(String instalTransInfo) {
        this.instalTransInfo = instalTransInfo;
    }

    public String getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(String customerInfo) {
        this.customerInfo = customerInfo;
    }

    public String getCardTransData() {
        return cardTransData;
    }

    public void setCardTransData(String cardTransData) {
        this.cardTransData = cardTransData;
    }

    public String getAccountPayChannel() {
        return accountPayChannel;
    }

    public void setAccountPayChannel(String accountPayChannel) {
        this.accountPayChannel = accountPayChannel;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public String getCustomerIp() {
        return customerIp;
    }

    public void setCustomerIp(String customerIp) {
        this.customerIp = customerIp;
    }

    public String getOrderTimeout() {
        return orderTimeout;
    }

    public void setOrderTimeout(String orderTimeout) {
        this.orderTimeout = orderTimeout;
    }

    public String getAccSplitData() {
        return accSplitData;
    }

    public void setAccSplitData(String accSplitData) {
        this.accSplitData = accSplitData;
    }

    public String getRiskRateInfo() {
        return riskRateInfo;
    }

    public void setRiskRateInfo(String riskRateInfo) {
        this.riskRateInfo = riskRateInfo;
    }

    public String getCtrlRule() {
        return ctrlRule;
    }

    public void setCtrlRule(String ctrlRule) {
        this.ctrlRule = ctrlRule;
    }

    public String getDefaultPayType() {
        return defaultPayType;
    }

    public void setDefaultPayType(String defaultPayType) {
        this.defaultPayType = defaultPayType;
    }

    public String getReqReserved() {
        return reqReserved;
    }

    public void setReqReserved(String reqReserved) {
        this.reqReserved = reqReserved;
    }

    public String getFrontFailUrl() {
        return frontFailUrl;
    }

    public void setFrontFailUrl(String frontFailUrl) {
        this.frontFailUrl = frontFailUrl;
    }

    public String getSupPayType() {
        return supPayType;
    }

    public void setSupPayType(String supPayType) {
        this.supPayType = supPayType;
    }

    public String getPayTimeout() {
        return payTimeout;
    }

    public void setPayTimeout(String payTimeout) {
        this.payTimeout = payTimeout;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getUserMac() {
        return userMac;
    }

    public void setUserMac(String userMac) {
        this.userMac = userMac;
    }

    @Override
    public String getApiPath() {
        return API_PATH;
    }

    @Override
    public Class<UnionPayFrontTransResponse> getResponseClass() {
        return UnionPayFrontTransResponse.class;
    }
}
