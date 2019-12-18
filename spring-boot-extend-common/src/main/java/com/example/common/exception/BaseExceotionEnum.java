package com.example.common.exception;

/**
 * @version 1.0
 * @ClassName BaseExceotionEnum
 * @Description 异常枚举类
 * @Author mingj
 * @Date 2019/12/17 22:44
 **/
public enum BaseExceotionEnum {

    RESOURCE_LOAD_ERROR("ERR_001", "资源文件加载失败"),
    INITIALIZE_FILTER_ERROR("ERR_002", "Dubbo过滤器初始化失败")
    ;

    private String code;

    private String message;

    BaseExceotionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
