package com.example.common.exception;

/**
 * @version 1.0
 * @ClassName BaseException
 * @Description TODO描述
 * @Author mingj
 * @Date 2019/10/20 22:55
 **/
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -8391393062479120200L;

    private String code;

    private String msg;

    private Boolean status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public BaseException(String code, String msg, Boolean status) {
        this.code = code;
        this.msg = msg;
        this.status = status;
    }

    public BaseException() {
    }

    public BaseException(Throwable cause, String code, String msg, Boolean status) {
        super(cause);
        this.code = code;
        this.msg = msg;
        this.status = status;
    }
}
