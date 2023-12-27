package cse.ooad.project.controller;

import cse.ooad.project.model.Student;
import cse.ooad.project.model.Teacher;
import cse.ooad.project.service.LoginService;
import cse.ooad.project.service.websocket.WsSessionManager;
import cse.ooad.project.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
//@RequestMapping("/api")

public class AuthController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Result<List<Object>> login(@RequestBody Map<String, Object> jsonMap) {
        System.out.println(jsonMap);
        String username = (String) jsonMap.get("username");
        String password = (String) jsonMap.get("password");
        boolean isTeacher = false;
//        boolean isTeacher = (boolean) jsonMap.get("isTeacher");
        if (isTeacher) {
            Teacher result = loginService.loginTeacher(username, password);
            if (result == null) {
                log.info(username + " Invalid username or password");
                return Result.error("Invalid username or password");
            }
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", result.getTeacherId());
            claims.put("username", username);
            claims.put("isTeacher", true);
            String jwt = JwtUtils.generateToken(claims);
            log.info(result.getTeacherId() + "Login successfully");
            List<Object> list = List.of(jwt, result);
            return Result.success("success", list);
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
            List<Object> list = List.of(jwt, result);
            return Result.success("success", list);
        }

    }


    @PostMapping("/logout")
    public Result<String> logout(@RequestHeader("Authorization") String token) {
        //TODO: delete token from database

        return Result.success("Logout successfully", null);
    }


}
