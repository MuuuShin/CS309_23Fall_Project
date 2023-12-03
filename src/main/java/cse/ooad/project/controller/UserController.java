package cse.ooad.project.controller;


import cse.ooad.project.model.Student;
import cse.ooad.project.service.SearchService;
import cse.ooad.project.service.StudentService;
import cse.ooad.project.service.TeacherService;
import cse.ooad.project.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin("*")
public class UserController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private SearchService searchService;

    @GetMapping("/users")
    public Result<List<Student>> getAllUsers(@RequestHeader("Authorization") String token) {

        log.info("get all users");
        try {
            Claims claims = JwtUtils.parseJWT(token);
            if (claims.get("isTeacher").equals(true)) {
                List<Student> students = searchService.searchStudents();
//                List<Student> students = null;
                return Result.success("success", students);
            }
            else {
//                List<Student> students = studentService.searchAllStudents();
                return Result.error("error");
            }
        }
        catch (Exception e) {
            return Result.error("error");
        }


//        return Result.success("success", students);
    }

    @GetMapping("/users/{name}")
    public Result<List<Student>> getUserByUsername(@PathVariable("name") String name) {
        log.info("get user by name");
        log.info("name: {}", name);
        List<Student> student = searchService.searchStudentByName(name);
        if (student != null) {
            return Result.success("success", student);
        }
        return Result.error("error");
    }

    @DeleteMapping("/users/{id}")
    public Result<String> deleteUserById(String id) {
        log.info("delete user by id");
        boolean b = teacherService.deleteStudent(Long.parseLong(id));
//        boolean b = false;
        if (b) {
            return Result.success("success", null);
        }
        return Result.error("error");
    }

    @PutMapping("/users")
    public Result<String> updateUserById(@RequestBody Student student) {
        log.info("update user by id");
        //TODO 有问题
        Student s = teacherService.updateStudent(student);
//        boolean b = false;
        if (s != null) {
            return Result.success("success", null);
        }
        return Result.error("error");
    }

//    @PostMapping("/users")
//    public Result<String> addUser() {
//        log.info("add user");
//
//    }

    @PutMapping("/users/{id}")
    public Result<String> updateUserById(@PathVariable("id") String id, @RequestBody Student student, @RequestHeader("Authorization") String token) {

        Claims claims;
        try {
            claims = JwtUtils.parseJWT(token);
        } catch (Exception e) {
            return Result.error("id not match");
        }
        String userId = claims.get("id").toString();
        if (!userId.equals(id)) {
            return Result.error("fail");
        }

        student.setStudentId(Long.parseLong(id));

        log.info("update user by id");
        Student s = studentService.changeIntroduce(student);
//        boolean b = false;
        if (s != null) {
            return Result.success("success", null);
        }
        return Result.error("error");

    }





}