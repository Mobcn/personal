package com.mobingc.personal.common.enums;


/**
 * 响应代码枚举
 *
 * @author Mo
 * @since 2022-08-25
 */
public enum ResultCode {

    ERROR(-1, "未知错误"),
    OK(0, "请求成功"),
    NULL_POINTER_ERROR(5001, "空指针异常"),
    BUSINESS_ERROR(6000, "业务异常"),
    MISSING_SETTING_PROPERTY(6001, "缺少配置项!", BUSINESS_ERROR),
    LOGIN_FAIL(6002, "用户名或密码有误!", BUSINESS_ERROR),
    USER_IS_DISABLE(6003, "用户不可用!", BUSINESS_ERROR),
    VALIDATE_ERROR(7000, "参数校验异常"),
    ID_IS_EMPTY(7001, "ID为空!", VALIDATE_ERROR),
    IMAGE_TYPE_IS_EMPTY(7101, "图片文件后缀名为空!", VALIDATE_ERROR),
    IMAGE_BASE64_IS_EMPTY(7102, "图片base64字符串为空!", VALIDATE_ERROR),
    IMAGE_BASE64_IS_NOT_MATCHES(7103, "非图片base64字符串!", VALIDATE_ERROR),
    TAG_IS_NOT_FOUND(7201, "标签不存在!", VALIDATE_ERROR),
    TAG_ID_IS_EMPTY(7202, "标签ID为空!", VALIDATE_ERROR),
    TAG_NAME_IS_EMPTY(7203, "标签名为空!", VALIDATE_ERROR),
    TAG_NAME_IS_REPEATED(7204, "标签名重复!", VALIDATE_ERROR),
    USER_NAME_IS_EMPTY(7301, "用户名为空!", VALIDATE_ERROR),
    PASSWORD_IS_EMPTY(7302, "密码为空!", VALIDATE_ERROR),
    CATEGORY_IS_NOT_FOUND(7401, "分类不存在!", VALIDATE_ERROR),
    CATEGORY_ID_IS_EMPTY(7402, "分类ID为空!", VALIDATE_ERROR),
    CATEGORY_NAME_IS_EMPTY(7403, "分类名为空!", VALIDATE_ERROR),
    CATEGORY_NAME_IS_REPEATED(7404, "分类名重复!", VALIDATE_ERROR),
    CATEGORY_DESCRIPTION_IS_EMPTY(7405, "分类描述为空!", VALIDATE_ERROR),
    ARTICLE_ID_IS_EMPTY(7501, "文章ID为空!", VALIDATE_ERROR),
    ARTICLE_TITLE_IS_EMPTY(7502, "文章标题为空!", VALIDATE_ERROR),
    ARTICLE_DESCRIPTION_IS_EMPTY(7503, "文章描述为空!", VALIDATE_ERROR),
    ARTICLE_IS_NOT_FOUND(7504, "文章不存在!", VALIDATE_ERROR),
    SETTING_IS_NOT_FOUND(7601, "设置不存在!", VALIDATE_ERROR),
    SETTING_VALUE_IS_INCOMPLETE(7602, "设置项值不完整!", VALIDATE_ERROR);

    /**
     * 响应代码
     */
    private final Integer code;

    /**
     * 响应消息
     */
    private final String message;

    /**
     * 响应类别
     */
    private final ResultCode type;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.type = null;
    }

    ResultCode(Integer code, String message, ResultCode type) {
        this.code = code;
        this.message = message;
        this.type = type;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public ResultCode getType() {
        return this.type;
    }

    public Integer getTypeCode() {
        return this.type != null ? this.type.code : null;
    }

    public String getTypeMessage() {
        return this.type != null ? this.type.message : null;
    }

}
