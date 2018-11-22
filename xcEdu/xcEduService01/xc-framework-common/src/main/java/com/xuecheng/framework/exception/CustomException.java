package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * 自定义异常
 *
 * @author LiYuan
 * Created on 2018/11/23.
 */
public class CustomException extends RuntimeException {

    /**
     * 错误代码
     */
    private ResultCode resultCode;

    public CustomException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

}
