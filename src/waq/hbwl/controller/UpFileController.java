package waq.hbwl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
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

}
