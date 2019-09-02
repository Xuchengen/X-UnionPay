package com.github.xuchengen.request;


import com.github.xuchengen.UnionPayRequest;
import com.github.xuchengen.response.UnionPayFileTransferResponse;

/**
 * 在线网关支付--文件传输接口请求模型
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/8/30
 */
public class UnionPayFileTransferRequest extends UnionPayCommonRequest implements UnionPayRequest<UnionPayFileTransferResponse> {

    /**
     * 产品类型
     */
    private String bizType = "000000";

    /**
     * 清算日期
     */
    private String settleDate;

    /**
     * 文件类型
     */
    private String fileType;

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

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getReqReserved() {
        return reqReserved;
    }

    public void setReqReserved(String reqReserved) {
        this.reqReserved = reqReserved;
    }

    @Override
    public String getApiPath() {
        return null;
    }

    @Override
    public Class<UnionPayFileTransferResponse> getResponseClass() {
        return UnionPayFileTransferResponse.class;
    }
}
