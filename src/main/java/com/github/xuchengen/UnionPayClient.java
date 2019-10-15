package com.github.xuchengen;

/**
 * 银联客户端接口
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/8/28
 */
public interface UnionPayClient {

    /**
     * 执行方法
     *
     * @param request 请求对象
     * @param <T>     UnionpayRequest的子类
     * @return UnionpayResponse的子类
     * @throws UnionPayException 异常
     */
    <T extends UnionPayResponse> T pageExecute(UnionPayRequest<T> request) throws UnionPayException;

    /**
     * 执行方法
     *
     * @param request 请求对象
     * @param <T>     UnionpayRequest的子类
     * @return UnionpayResponse的子类
     * @throws UnionPayException 异常
     */
    <T extends UnionPayResponse> T execute(UnionPayRequest<T> request) throws UnionPayException;

    /**
     * 解密
     *
     * @param dataStr 密文数据字符串
     * @return 明文数据字符串
     */
    String decrypt(String dataStr);

    /**
     * 加密
     *
     * @param dataStr 明文数据字符串
     * @return 密文数据字符串
     */
    String encrypt(String dataStr);
}