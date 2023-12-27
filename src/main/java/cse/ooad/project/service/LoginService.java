package cse.ooad.project.service;


import cse.ooad.project.model.Student;
import cse.ooad.project.model.Teacher;
import cse.ooad.project.repository.PasswordRepository;
import cse.ooad.project.repository.StudentRepository;
import cse.ooad.project.repository.TeacherRepository;

import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    PasswordRepository passwordRepository;



    /**
     * 学生登录
     * @param account 账号
     * @param password 密码
     * @return
     */
    public Student loginStudent(String account, String password) {
        System.out.println("account: " + account + " password: " + password);
        Student student = studentRepository.getStudentByAccount(account);
        if (student != null) {
            String paw = passwordRepository.findPasswordByAccount(account).getPassword();
            if (paw.equals(password)) {
                return student;
            }
        }

        return null;
    }

    /**
     * 老师登录
     * @param account 账号
     * @param password 密码
     * @return
     */
    public Teacher loginTeacher(String account, String password) {
        Teacher teacher = teacherRepository.getTeacherByAccount(account);
        if (teacher != null) {
            String paw = passwordRepository.findPasswordByAccount(account).getPassword();
            if (paw.equals(password)) {
               return teacher;
            }
        }

        return null;
    }

}
