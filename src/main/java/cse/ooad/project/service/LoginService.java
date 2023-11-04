package cse.ooad.project.service;


import cse.ooad.project.model.Student;
import cse.ooad.project.model.Teacher;
import cse.ooad.project.repository.StudentRepository;
import cse.ooad.project.repository.TeacherRepository;
import cse.ooad.project.service.websocket.WsSessionManager;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    TeacherRepository teacherRepository;

    String checkLoginStudentVaildAndGetSession(String account, String password) {
        //打算加盐处理密码，但是目前还没有进行处理
        Student student = studentRepository.getStudentByAccount(account);
        if (student != null) {
            String paw = student.getPassword();
            if (paw.equals(password)) {
                String sessionId = createSessionId();
                WsSessionManager.addLoginUser(student.getStudentId(), sessionId);
                return sessionId;
            }
        }

        return null;
    }

    String checkLoginTeacherAndGetSession(String account, String password) {
        Teacher teacher = teacherRepository.getTeacherByAccount(account);
        if (teacher != null) {
            String paw = teacher.getPassword();
            if (paw.equals(password)) {
                String sessionId = createSessionId();
                WsSessionManager.addLoginUser(teacher.getTeacherId(), sessionId);
                return sessionId;
            }
        }

        return null;
    }

    String createSessionId() {
        //随机生成一个session，暂定方案
        Random random = new Random();
        return random.nextInt(100000000, 200000000) + "";
    }

}
