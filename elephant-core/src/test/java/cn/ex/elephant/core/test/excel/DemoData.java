package cn.ex.elephant.core.test.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author weixin 2021/8/19 3:06 下午
 */
@Data
public class DemoData {
    @ExcelProperty(value = "字符串标题")
    private String string;

    @ExcelProperty( value = "日期标题")
    private String date;

    @ExcelProperty(value = "数字标题")
    private String doubleData;
}
