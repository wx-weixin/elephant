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
public enum CardTypeEnum  {

    CONSTANT_1(1, "万"),

    CONSTANT_2(2, "饼"),

    CONSTANT_3(3, "条"),

    CONSTANT_4(4, "字"),

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
    public static CardTypeEnum of(Integer code) {
        return Arrays.stream(values()).filter(anEnum -> anEnum.getCode().equals(code)).findFirst().orElse(null);
    }


}