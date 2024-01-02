package cse.ooad.project.controller;


import cse.ooad.project.service.StudentService;
import cse.ooad.project.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@CrossOrigin("*")
public class BatchController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    @PostMapping("/users/import")
    public Result<String> importUser(@RequestParam("file")MultipartFile file) {
        log.info("import user");
        File tempFile = null;
        if (file != null && !file.isEmpty()) {
            try {
                // 创建一个临时文件
                tempFile = File.createTempFile("upload-", file.getOriginalFilename());
                // 将上传的文件内容写入临时文件
                file.transferTo(tempFile);
                // 接下来可以使用 tempFile 进行后续处理
            } catch (IOException e) {
                // 处理异常
                log.error("batch error", e);
            }
        }
        //TODO: import user from csv file
        teacherService.batchSaveStudent(tempFile);
//        if (b) {
//            return Result.success("success", null);
//        }
        return Result.success("success", null);
    }


    @PostMapping("/dormitories/import")
    public Result<String> importDormitory(@RequestParam("file")MultipartFile file) {
        log.info("import dormitory");
        File tempFile = null;
        if (file != null && !file.isEmpty()) {
            try {
                // 创建一个临时文件
                tempFile = File.createTempFile("upload-", file.getOriginalFilename());
                // 将上传的文件内容写入临时文件
                file.transferTo(tempFile);
                // 接下来可以使用 tempFile 进行后续处理
            } catch (IOException e) {
                // 处理异常
                log.error("batch error", e);
            }
        }
        //TODO: import dormitory from csv file
        teacherService.batchSaveRoom(tempFile);
//        if (b) {
//            return Result.success("success", null);
//        }
//        return Result.error("error");
        return Result.success("success", null);
    }

    @GetMapping("/users/export")
    public ResponseEntity<Resource> exportUser() throws IOException {
        log.info("export user");
        File file = teacherService.batchOutputStudent();
        String filepath = file.getAbsolutePath();
        // 创建资源对象
        Resource resource =  new FileSystemResource(filepath);
        // 设置响应头，控制浏览器下载该文件
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment;filename=" + URLEncoder.encode("output.csv",
                StandardCharsets.UTF_8));
        headers.set("content-length", String.valueOf(resource.contentLength()));
        // 返回响应实体
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
