package org.webmaple.common.model;

import org.webmaple.common.enums.CommonErrorCode;

/**
 * @author lyifee
 * on 2020/12/27
 */
public class Result <T> {
    private String message;

    private Boolean success;

    private T model;

    public Result () {}

    private Result(String message, boolean success, T model) {
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

    public Result<T> success(T model) {
        return new Result<T>(null, true, model);
    }

    public Result<T> fail(T model) {
        return new Result<T>(null, false, model);
    }

    public Result<T> success(String message, T model) {
        return new Result<T>(message, true, model);
    }

    public Result<T> fail(String message, T model) {
        return new Result<>(message, false, model);
    }

    public Result<T> fail(CommonErrorCode errorCode, T model) {
        return new Result<T>(errorCode.getCode(), false, model);
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

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }
}

