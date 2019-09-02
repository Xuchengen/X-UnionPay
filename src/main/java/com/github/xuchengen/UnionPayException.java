package com.github.xuchengen;

/**
 * 银联支付异常类
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/8/29
 */
public class UnionPayException extends Exception {

    private static final long serialVersionUID = 8689355700637811882L;

    public UnionPayException() {
        super();
    }

    public UnionPayException(String message) {
        super(message);
    }

    public UnionPayException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnionPayException(Throwable cause) {
        super(cause);
    }

}
