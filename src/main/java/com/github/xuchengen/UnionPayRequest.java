package com.github.xuchengen;

/**
 * 请求接口
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/9/2
 */
public interface UnionPayRequest<T extends UnionPayResponse> {

    /**
     * 获取API路径
     *
     * @return API路径
     */
    String getApiPath();

    /**
     * 获取响应类型
     *
     * @return 响应类型
     */
    Class<T> getResponseClass();
}
