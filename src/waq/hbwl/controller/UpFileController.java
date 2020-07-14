package waq.hbwl.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Controller
public class UpFileController {

    @RequestMapping(value = "/fileUpload")
    public String handleFormUpload(@RequestParam("name") String name, @RequestParam("upfile") List<MultipartFile> upfile, HttpServletRequest request) throws IOException {

        // 判断所上传文件是否存在
        if (!upfile.isEmpty()) {

            // 循环遍历上传的文件
            for (MultipartFile file : upfile) {

                // 获取上传文件的原始名称
                String originalFilename = file.getOriginalFilename();
                // 设置上传文件的保存地址
                String dirPath = request.getServletContext().getRealPath("/upload/");
                File filePath = new File(dirPath);
                // 判断地址是否存在
                if (!filePath.exists()) {
                    filePath.mkdirs();
                }
                // 使用UUID重新命名
                String newFilename = name + "_" + UUID.randomUUID() + "_" + originalFilename;
                // 使用MultipartFile接口上传文件到指定位置
                file.transferTo(new File(dirPath + newFilename));
                System.out.println(dirPath + newFilename);

            }
            return "success";

        } else {
            return "error";
        }

    }

    @RequestMapping(value = "download")
    public ResponseEntity<byte[]> fileDownload(HttpServletRequest request, String filename) throws Exception {

        // 指定下载的路径
        String path = request.getServletContext().getRealPath("/download");
        // 创建该文件对象
        File file = new File(path + File.separator + filename);
        // 对文件名编码，防止中文乱码
        filename = this.getFilename(request, filename);
        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        // 通知浏览器以下载方式打开文件
        headers.setContentDispositionFormData("attachment", filename);
        // 定义以流的形式下载返回文件数据
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        // 使用mvc框架的ResponseEntity对象封装返回下载数据
        return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);

    }

    // 根据浏览器的不同进行编码设置，返回编码后的文件名
    private String getFilename(HttpServletRequest request, String filename) {

        // IE不同版本，User-Agent中出现的关键词
        String[] IEBrowserKeyWords = {"MSIE", "Trident", "Edge"};
        // 获取请求头的代理信息
        String userAgent = request.getHeader("User-Agent");
        for (String key : IEBrowserKeyWords) {
            if (userAgent.contains(key)) {
                // IE内核浏览器统一为UTF-8
                return URLEncoder.encode(filename, StandardCharsets.UTF_8);
            }
        }
        // 其他浏览器
        return new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);

    }

}
