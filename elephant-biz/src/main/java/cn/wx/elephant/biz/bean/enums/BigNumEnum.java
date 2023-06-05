package cn.wx.elephant.biz.bean.enums;

import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author weixin 2023/6/2 2:26 PM
 */
@Getter
@AllArgsConstructor
public enum BigNumEnum {

    CONSTANT_1(1, "一"),

    CONSTANT_2(2, "二"),

    CONSTANT_3(3, "三"),

    CONSTANT_4(4, "四"),

    CONSTANT_5(5, "五"),

    CONSTANT_6(6, "六"),

    CONSTANT_7(7, "七"),

    CONSTANT_8(8, "八"),

    CONSTANT_9(9, "九"),


    ;

    @JsonValue
    private final Integer code;

    private final String title;

    /**
     * 判断参数code是否是一个有效的枚举
     */
    public static boolean valid(Integer code) {
        return Arrays.stream(values()).anyMatch(anEnum -> anEnum.getCode().equals(code));
    }

    /**
     * 获取code对应的枚举
     */
    @JsonCreator
    public static BigNumEnum of(Integer code) {
        return Arrays.stream(values()).filter(anEnum -> anEnum.getCode().equals(code)).findFirst().orElse(null);
    }

}