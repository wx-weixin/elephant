package cn.ex.elephant.core.test.excel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.google.common.collect.Lists;

/**
 * @author weixin 2023/6/20 9:39 AM
 */
public class TestExcel {


    private static List<List<String>> head() {
        List<List<String>> headTitle = Lists.newArrayList();
        String title = "标题";
        String describe = "描述 \n" + "1.aaaa\n" + "2.bbbbb\n" + "3.cccc\n"
                + "4.dddd\n" + "5.eeeeee\n" + "6.fffff\n";
        headTitle.add(Lists.newArrayList(title, describe, "字符串标题"));
        headTitle.add(Lists.newArrayList(title, describe, "日期标题"));
        headTitle.add(Lists.newArrayList(title, describe, "数字标题"));

        return headTitle;
    }

    private static List<DemoData> getData() {
        List<DemoData> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData demoData = new DemoData();
            demoData.setString("123");
            demoData.setDate(new Date().toString());
            demoData.setDoubleData(i + "");
            data.add(demoData);
        }
        return data;
    }

    private static List<List<String>> getDataList() {
        List<List<String>> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<String> list = new ArrayList<>();
            data.add(Lists.newArrayList("123",new Date().toString(),(i+"")));
        }
        return data;
    }

    @Test
    @DisplayName("创建动态表头excel1")
    public void test1(){
        File file = new File("/Users/zaizai/Documents/工作簿1.xlsx");
        EasyExcel.write(file)
                // 这里放入动态头
                .head(head()).sheet("sheet1")
                // 用 List<List<String>> 去传入
                .doWrite(getDataList());
    }

    @Test
    @DisplayName("创建动态表头excel2")
    public void test2(){
        // 设置背景颜色
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, new WriteCellStyle());

        File file = new File("/Users/zaizai/Documents/excel/工作簿2.xlsx");
        EasyExcel.write(file)
                // 这里放入动态头
                .head(head()).sheet("sheet1")
                .registerWriteHandler(horizontalCellStyleStrategy)
                // 用对象集合 去传入
                .doWrite(getData());
    }


    @Test
    @DisplayName("创建动态表头excel，返回 MultipartFile")
    public void test3(){
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, new WriteCellStyle());

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        EasyExcel.write(out)
                .head(head())
                .sheet("sheet1")
                .registerWriteHandler(horizontalCellStyleStrategy)
                // 用对象集合 去传入
                .doWrite(getData());

        // MultipartFile 主要是用来做文件网络传输
        MultipartFile multipartFile = new MockMultipartFile("文件1", "源文件名", MediaType.MULTIPART_FORM_DATA_VALUE, out.toByteArray());

        // 将MultipartFile写入本地
        File file = new File("/Users/zaizai/Documents/excel/工作簿3.csv");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
