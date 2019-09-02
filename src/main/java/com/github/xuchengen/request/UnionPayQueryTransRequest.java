package com.github.xuchengen.request;


import com.github.xuchengen.UnionPayRequest;
import com.github.xuchengen.response.UnionPayQueryTransResponse;

/**
 * 在线网关支付--交易状态查询请求模型
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/8/30
 */
public class UnionPayQueryTransRequest extends UnionPayCommonRequest implements UnionPayRequest<UnionPayQueryTransResponse> {

    private static final String API_PATH = "/gateway/api/queryTrans.do";

    /**
     * 产品类型
     */
    private String bizType = "000000";

    /**
     * 商户订单号
     */
    private String orderId;

    /**
     * 保留域
     */
    private String reserved;

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    @Override
    public String getApiPath() {
        return API_PATH;
    }

    @Override
    public Class<UnionPayQueryTransResponse> getResponseClass() {
        return UnionPayQueryTransResponse.class;
    }
}
