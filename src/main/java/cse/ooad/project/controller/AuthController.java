package cse.ooad.project.controller;

import cse.ooad.project.model.Student;
import cse.ooad.project.model.Teacher;
import cse.ooad.project.service.LoginService;
import cse.ooad.project.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Result<String> login(@RequestParam String username, @RequestParam String password, @RequestBody boolean isTeacher) {
        if (isTeacher) {
            Teacher result = loginService.loginTeacher(username, password);
            if (result == null) {
                log.info(username + "Invalid username or password");
                return Result.error("Invalid username or password");
            }
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", result.getTeacherId());
            claims.put("username", username);
            claims.put("isTeacher", true);
            String jwt = JwtUtils.generateToken(claims);
            log.info(result.getTeacherId() + "Login successfully");
            return Result.success("success", jwt);
        } else {
            Student result = loginService.loginStudent(username, password);
            if (result == null) {
                log.info(username + "Invalid username or password");
                return Result.error("Invalid username or password");
            }
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", result.getStudentId());
            claims.put("username", username);
            claims.put("isTeacher", false);
            String jwt = JwtUtils.generateToken(claims);
            log.info(result.getStudentId() + "Login successfully");
            return Result.success("success", jwt);
        }

    }


    @PostMapping("/logout")
    public Result<String> logout(@RequestHeader("Authorization") String token) {
        //TODO: delete token from database

        return Result.success("Logout successfully", null);
    }


}
