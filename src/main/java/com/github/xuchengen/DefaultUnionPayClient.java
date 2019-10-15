package com.github.xuchengen;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.github.xuchengen.helper.UnionPayHelper;
import com.github.xuchengen.request.UnionPayFileTransferRequest;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * 默认实现银支付联客户端
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019-08-26
 */
public class DefaultUnionPayClient implements UnionPayClient {

    private static final Log log = LogFactory.get(UnionPayConstants.UNIONPAY_LOG);

    /**
     * 银联配置
     */
    private UnionPayConfig config;

    /**
     * 构造方法
     *
     * @param config 银联配置
     */
    public DefaultUnionPayClient(UnionPayConfig config) {
        this.config = config;
    }

    public UnionPayConfig getConfig() {
        return config;
    }

    public DefaultUnionPayClient setConfig(UnionPayConfig config) {
        this.config = config;
        return this;
    }

    @Override
    public <T extends UnionPayResponse> T pageExecute(UnionPayRequest<T> request) throws UnionPayException {
        try {
            String apiUrl = this.config.getApiHost() + request.getApiPath();

            Map<String, String> paramMap = buildParamMap(request);

            String submitFormStr = UnionPayHelper.buildSubmitFormStr(apiUrl, paramMap, this.config.getEncoding());

            Class<T> responseClass = request.getResponseClass();

            T t = responseClass.newInstance();

            t.setBody(submitFormStr);
            return t;
        } catch (Exception e) {
            throw new UnionPayException(e);
        }
    }

    @Override
    public <T extends UnionPayResponse> T execute(UnionPayRequest<T> request) throws UnionPayException {
        try {
            String apiUrl = this.config.getApiHost() + request.getApiPath();
            if (request instanceof UnionPayFileTransferRequest) {
                apiUrl = this.config.getFileDownloadHost();
            }

            Map<String, String> paramMap = buildParamMap(request);

            String reqStr = UnionPayHelper.buildKVPairStrWithURLEncode(paramMap, this.config.getEncoding());

            if (log.isDebugEnabled()) {
                log.debug("请求银联参数：[{}]", reqStr);
            }

            HttpResponse response = HttpUtil.createPost(apiUrl)
                    .body(reqStr)
                    .execute();

            if (!response.isOk()) {
                throw new UnionPayException(StrUtil.format("请求银联接口失败：接口地址：[{}],HTTP响应：[{}]", apiUrl, response.toString()));
            }

            String respStr = response.body();

            if (log.isDebugEnabled()) {
                log.debug("银联响应参数：[{}]", respStr);
            }

            Map<String, String> respMap;
            try {
                respMap = UnionPayHelper.buildMapByKVPairStr(respStr);

                if (CollUtil.isEmpty(respMap)) {
                    throw new UnionPayException("银联响应参数转为Map集合元素为空");
                }

            } catch (Exception e) {
                throw new UnionPayException(StrUtil.format("将银联响应参数转为Map集合异常：[{}]", e.getMessage()), e);
            }

            boolean verify = this.config.getSigner().verify(respMap, respMap.get(UnionPayConstants.VAR_ENCODING));

            if (!verify) {
                throw new UnionPayException("银联返回参数验签失败");
            }

            Class<T> responseClass = request.getResponseClass();

            return BeanUtil.mapToBean(respMap, responseClass, false);

        } catch (Exception e) {
            throw new UnionPayException(e);
        }
    }

    @Override
    public String decrypt(String dataStr) {
        RSA rsa = SecureUtil.rsa(config.getSigner().getMerchantPrivateKeyStr(), null);
        return rsa.decryptStr(dataStr, KeyType.PrivateKey);
    }

    @Override
    public String encrypt(String dataStr) {
        String encryptCertStr = config.getSigner().getEncryptCertStr();
        byte[] publicKeyByte = UnionPayHelper.getCertFromCertStr(encryptCertStr, true).getPublicKey().getEncoded();
        RSA rsa = SecureUtil.rsa(null, publicKeyByte);
        return rsa.encryptBase64(dataStr, KeyType.PublicKey);
    }

    /**
     * 构建参数Map
     *
     * @param request 请求对象
     * @return Map
     */
    private Map<String, String> buildParamMap(UnionPayRequest request) {
        TreeMap<String, Object> toMap = new TreeMap<>();
        BeanUtil.beanToMap(request, toMap, false, true);

        Map<String, String> paramMap = Convert.convert(new TypeReference<Map<String, String>>() {
        }, toMap);

        paramMap.put(UnionPayConstants.VAR_MERCHANT_ID, this.config.getMerchantId());
        paramMap.put(UnionPayConstants.VAR_VERSION, this.config.getSigner().getVersion());
        paramMap.put(UnionPayConstants.VAR_SIGN_METHOD, this.config.getSigner().getSignMethod());
        paramMap.put(UnionPayConstants.VAR_CERT_ID, this.config.getSigner().getCertId());
        paramMap.put(UnionPayConstants.VAR_ENCODING, this.config.getEncoding());
        paramMap.put(UnionPayConstants.VAR_TXN_TIME, DateUtil.format(new Date(), UnionPayConstants.DEFAULT_DATE_TIME_FORMAT));

        String kvPairStr = UnionPayHelper.buildKVPairStr(paramMap);

        String digestStr = this.config.getSigner().digest(kvPairStr, this.config.getEncoding());

        String signStr = this.config.getSigner().sign(digestStr);

        paramMap.put(UnionPayConstants.VAR_SIGNATURE, signStr);

        if (log.isDebugEnabled()) {

            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                log.debug("[{}]<===>[{}]", entry.getKey(), entry.getValue());
            }

            log.debug("键值对参数字符串：[{}]", kvPairStr);
            log.debug("摘要字符串：[{}]", digestStr);
            log.debug("签名字符串：[{}]", signStr);
        }

        return paramMap;
    }
}
