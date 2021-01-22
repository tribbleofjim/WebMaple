package com.webmaple.common.model;

import com.webmaple.common.enums.CommonErrorCode;

/**
 * @author lyifee
 * on 2020/12/27
 */
public class Result <T> {
    private String message;

    private Boolean success;

    private Object model;

    public Result () {}

    private Result(String message, boolean success, Object model) {
        this.message = message;
        this.success = success;
        this.model = model;
    }

    public Result<T> success() {
        return new Result<>(null, true, null);
    }

    public Result<T> fail() {
        return new Result<>(null, false, null);
    }

    public Result<T> success(String message) {
        return new Result<>(message, true, null);
    }

    public Result<T> fail(String message) {
        return new Result<>(message, false, null);
    }

    public Result<T> fail(CommonErrorCode errorCode) {
        return new Result<>(errorCode.getCode(), false, null);
    }

    public Result<T> success(Object model) {
        return new Result<>(null, true, model);
    }

    public Result<T> fail(Object model) {
        return new Result<>(null, false, model);
    }

    public Result<T> success(String message, Object model) {
        return new Result<>(message, true, model);
    }

    public Result<T> fail(String message, Object model) {
        return new Result<>(message, false, model);
    }

    public Result<T> fail(CommonErrorCode errorCode, Object model) {
        return new Result<>(errorCode.getCode(), false, model);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }
}

