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
public enum CardWordEnum {

    CONSTANT_1(1, "东"),

    CONSTANT_2(2, "南"),

    CONSTANT_3(3, "西"),

    CONSTANT_4(4, "北"),

    CONSTANT_5(5, "红中"),

    CONSTANT_6(6, "发财"),

    CONSTANT_7(7, "白板"),

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
    public static CardWordEnum of(Integer code) {
        return Arrays.stream(values()).filter(anEnum -> anEnum.getCode().equals(code)).findFirst().orElse(null);
    }


}