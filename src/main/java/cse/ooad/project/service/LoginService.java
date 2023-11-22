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

    public Student loginStudent(String account, String password) {
        //打算加盐处理密码，但是目前还没有进行处理
        //todo 密码加盐
        Student student = studentRepository.getStudentByAccount(account);
        if (student != null) {
            String paw = passwordRepository.findPasswordByAccount(account).getPassword();
            if (paw.equals(password)) {
                return student;
            }
        }

        return null;
    }

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
