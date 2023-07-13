package cn.wx.elephant.core.enums;

import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 基础公共状态码
 * @author weixin 2021/3/24 1:29 下午
 */
@Getter
@AllArgsConstructor
public enum BaseStatusCodeEnum implements EnumAncestor<String> {

    B000000("B00000", "操作成功"),
    B000001("B00001", "系统繁忙"),
    B000002("B00002", "登录超时，请重新登录"),
    B000003("B00003", "无权限，请联系管理员开通相关权限"),
    B000004("B00004", "数据异常"),
    B000005("B00005", "消息发送失败"),

    ;

    @JsonValue
    private final String code;

    private final String title;

    /**
     * 判断参数code是否是一个有效的枚举
     */
    public static boolean valid(String code) {
        return Arrays.stream(values()).anyMatch(anEnum -> anEnum.getCode().equals(code));
    }

    /**
     * 获取code对应的枚举
     */
    @JsonCreator
    public static BaseStatusCodeEnum of(String code) {
        return Arrays.stream(values()).filter(anEnum -> anEnum.getCode().equals(code)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return asJavabean().toString();
    }

}