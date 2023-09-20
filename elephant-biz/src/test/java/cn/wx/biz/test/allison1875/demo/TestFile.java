package cn.wx.biz.test.allison1875.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import cn.hutool.core.io.FileUtil;

/**
 * @author weixin 2023/9/13 4:35 PM
 */
public class TestFile {

    @Test
    public void testFile(){
        File directory = new File("/Users/zaizai/Downloads/aa"); // 替换为你要读取的目录路径
        recurFile(directory);

    }

    private void recurFile(File directory){
        // 判断目录是否存在
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles(); // 获取目录下的所有文件

            // 遍历文件数组
            assert files != null;
            for (File file : files) {
                // 判断是否为文件
                if (file.isFile()) {
                    System.out.println(file.getName()); // 输出文件名
                    copy(file,"/Users/zaizai/Downloads/bb/");
                }else if(file.isDirectory()){
                    System.out.println(file.getName()); // 输出目录名
                    recurFile(file);
                }
            }
        } else {
            System.out.println("目录不存在");
        }
    }

    private void copy(File sourceFile,String targetPath){
        try {
            Path source = sourceFile.toPath();
            Path target = Path.of(targetPath, source.getFileName().toString());

            // 使用Files类的copy方法复制文件，并指定复制选项为REPLACE_EXISTING
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("文件复制完成！");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void zipFile(){
        String sourceDir = "/Users/zaizai/Downloads/aa"; // 替换为你要压缩的目录路径
        String zipFile = "/Users/zaizai/Downloads/aa/a.zip"; // 替换为你要生成的ZIP文件路径

        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile))) {
            File fileToZip = new File(sourceDir);
            zipFile(fileToZip, fileToZip.getName(), zipOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            assert children != null;
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        try (FileInputStream fis = new FileInputStream(fileToZip)) {
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
        }
    }


    @Test
    public void remoteUrl(){
        // 源文件路径
        String sourceFilePath = "http://172.30.86.196:9000/spi75416a427355-pri/02dd9fb7-2005-485b-912f-dc3fd0e073b7.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=1ND2M1S913IDGKW1541P%2F20230915%2Fdoc%2Fs3%2Faws4_request&X-Amz-Date=20230915T013629Z&X-Amz-Expires=10000&X-Amz-SignedHeaders=host&X-Amz-Signature=3bf32e2135b0240f3950fd5c1f8dea6f362016b690fb0009ab4865f133a1876b";
        // 目标目录
        String targetDirectory = "/Users/zaizai/Downloads/bb/";

        try {
            URL url = new URL(sourceFilePath);
            // 打开连接
            URLConnection conn = url.openConnection();

            // 获取远程文件的名称
            String fileName = getName(url, conn);

            // 获取远程文件流
            InputStream inputStream = conn.getInputStream();

            // 创建本地文件输出流
            FileOutputStream outputStream = new FileOutputStream(targetDirectory+fileName);
            // 缓冲区大小
            byte[] buffer = new byte[1024];
            int bytesRead;

            // 将远程文件流写入本地文件
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // 关闭流
            inputStream.close();
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @NotNull
    private String getName(URL url, URLConnection conn) {
        String fileName = conn.getHeaderField("Content-Disposition");
        if (fileName != null && !fileName.isEmpty()) {
            int index = fileName.indexOf("filename=");
            if (index != -1) {
                fileName = fileName.substring(index + 9).replace("\"", "");
            }
        } else {
            fileName = url.getFile();
            if(fileName.contains("?")){
                fileName = fileName.substring(fileName.lastIndexOf("/") + 1,fileName.indexOf("?"));
            }else{
                fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
            }
        }
        return fileName;
    }

    @Test
    public void remoteUrl1(){
        String sourceFilePath = "http://172.30.86.196:9000/spi75416a427355-pri/02dd9fb7-2005-485b-912f-dc3fd0e073b7.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=1ND2M1S913IDGKW1541P%2F20230915%2Fdoc%2Fs3%2Faws4_request&X-Amz-Date=20230915T013629Z&X-Amz-Expires=10000&X-Amz-SignedHeaders=host&X-Amz-Signature=3bf32e2135b0240f3950fd5c1f8dea6f362016b690fb0009ab4865f133a1876b"; // 源文件路径
        String targetDirectory = "/Users/zaizai/Downloads/bb/"; // 目标目录

        try {
            URI url = new URI(sourceFilePath);
            File file = FileUtil.file(url);


            Path source = file.toPath();
            Path target = Path.of(targetDirectory, source.getFileName().toString());

            // 使用Files类的copy方法复制文件，并指定复制选项为REPLACE_EXISTING
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("文件复制完成！");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testString(){
        String sourceFilePath = "http://172.30.86.196:9000/spi75416a427355-pri/02dd9fb7-2005-485b-912f-dc3fd0e073b7.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=1ND2M1S913IDGKW1541P%2F20230915%2Fdoc%2Fs3%2Faws4_request&X-Amz-Date=20230915T013629Z&X-Amz-Expires=10000&X-Amz-SignedHeaders=host&X-Amz-Signature=3bf32e2135b0240f3950fd5c1f8dea6f362016b690fb0009ab4865f133a1876b"; // 源文件路径
        System.out.println(sourceFilePath.lastIndexOf("/") + 1);
        System.out.println(sourceFilePath.indexOf("??"));
        System.out.println(sourceFilePath.substring(sourceFilePath.lastIndexOf("/") + 1,sourceFilePath.indexOf("??")));
    }
}
