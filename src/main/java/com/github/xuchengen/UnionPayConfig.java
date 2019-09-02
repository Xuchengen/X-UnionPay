package com.github.xuchengen;

/**
 * 银联配置类
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/9/2
 */
public class UnionPayConfig {

    /**
     * 接口主机地址
     */
    private String apiHost;

    /**
     * 文件下载主机地址
     */
    private String fileDownloadHost;

    /**
     * 签名器
     */
    private Signer signer;

    /**
     * 字符编码
     */
    private String encoding;

    /**
     * 商户ID
     */
    private String merchantId;

    public String getApiHost() {
        return apiHost;
    }

    public UnionPayConfig setApiHost(String apiHost) {
        this.apiHost = apiHost;
        return this;
    }

    public String getFileDownloadHost() {
        return fileDownloadHost;
    }

    public UnionPayConfig setFileDownloadHost(String fileDownloadHost) {
        this.fileDownloadHost = fileDownloadHost;
        return this;
    }

    public Signer getSigner() {
        return signer;
    }

    public UnionPayConfig setSigner(Signer signer) {
        this.signer = signer;
        return this;
    }

    public String getEncoding() {
        return encoding;
    }

    public UnionPayConfig setEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public UnionPayConfig setMerchantId(String merchantId) {
        this.merchantId = merchantId;
        return this;
    }
}
