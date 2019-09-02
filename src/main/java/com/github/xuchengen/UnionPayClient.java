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
}