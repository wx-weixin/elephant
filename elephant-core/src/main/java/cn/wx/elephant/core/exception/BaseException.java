package cn.wx.elephant.core.exception;


import cn.wx.elephant.core.enums.EnumAncestor;

/**
 * @author weixin 2021/3/25 2:55 下午
 */
public class BaseException extends RuntimeException{

    private static final long serialVersionUID = 5943380621789168712L;

    /**
     * 异常编码
     */
    private String code;

    public BaseException(String code, String desc) {
        super(desc);
        this.code = code;
    }

    public BaseException(EnumAncestor<?> enumAncestor) {
        super(enumAncestor.getTitle());
        this.code = enumAncestor.getCode().toString();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
