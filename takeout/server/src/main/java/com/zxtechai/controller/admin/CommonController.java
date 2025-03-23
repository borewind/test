package com.zxtechai.controller.admin;

import com.zxtechai.result.Result;
import com.zxtechai.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;

@RestController
@Slf4j
@Api(tags = "公共相关接口")
@RequestMapping("/admin/common")
public class CommonController {
    private final Path imageStoragePath = Paths.get("server/src/main/resources/static/images"); // 图片存储路径 本地上传图片方法加入的代码
    private final String[] imageType = {".jpg",".png",".jpeg"};

    @Autowired
    private AliOssUtil aliOssUtil;
    /**
     * 图片上传
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("图片上传")
    public Result upload(MultipartFile file) throws IOException {
        log.info("上传图片{}",file);
        if(file.isEmpty()){
            return Result.error("图片为空,请上传图片");
        }
        try {

            //获取文件带格式全名
            String originalFilename = file.getOriginalFilename();
            //从最后的.出现的index截取 得到的就是格式.jpg/.png/.txt等
            String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
            if(Arrays.stream(imageType).noneMatch(substring::contains)){
                throw new IOException("不支持的文件格式");
            }
            //用随机uuid拼接格式得到最终文件名字
            String fileName = UUID.randomUUID().toString().replace("-", "")+substring;

            //本地上传打开

            // 设置保存文件夹
//            String folderPath = Paths.get("").toAbsolutePath().normalize().toString() + File.separator + imageStoragePath;//本地上传图片方法加入的代码
//            File folder = new File(folderPath);
//            if (!folder.exists()) {
//                folder.mkdirs();
//            }
//            File destFile = new File(folderPath + File.separator + fileName);//本地上传图片方法加入的代码
//            file.transferTo(destFile);//本地上传图片方法加入的代码
//            log.info("文件上传成功：" + destFile.getAbsolutePath());//本地上传图片方法加入的代码
//            return Result.success("http://localhost:8080/admin/common/image/"+fileName);//本地上传图片方法加入的代码

            //阿里云oss方式上传图片打开
            String uploadOss_url = aliOssUtil.upload(file.getBytes(), fileName);
            return Result.success(uploadOss_url);
        } catch (IOException e) {
//            e.printStackTrace();
            return Result.error("文件上传失败：" + e.getMessage());
        }

    }

    /**
     * 图片获取  本地上传图片方法加入的方法
     * @param filename
     * @return
     */
    @GetMapping(value = "/image/{filename:.+}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ApiOperation("图片获取")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        log.info("获取图片:{}",filename);
        try {
            Path file = imageStoragePath.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
