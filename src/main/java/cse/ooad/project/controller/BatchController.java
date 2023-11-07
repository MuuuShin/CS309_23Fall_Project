package cse.ooad.project.controller;


import cse.ooad.project.service.StudentService;
import cse.ooad.project.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Parameter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Slf4j
@RestController
@CrossOrigin("*")
public class BatchController {

    private TeacherService teacherService;
    private StudentService studentService;
    @PostMapping("/user/import")
    public Result<String> importUser(@RequestParam("file")MultipartFile file) {
        log.info("import user");

        //TODO: import user from csv file
        boolean b = teacherService.batchSaveStudent((File) file);
        if (b) {
            return Result.success("success", null);
        }
        return Result.error("error");
    }


    @PostMapping("/dormitories/import")
    public Result<String> importDormitory(@RequestParam("file")MultipartFile file) {
        log.info("import dormitory");
        //TODO: import dormitory from csv file
        boolean b = studentService.batchSaveDormitory((File) file);
        if (b) {
            return Result.success("success", null);
        }
        return Result.error("error");

    }
}
