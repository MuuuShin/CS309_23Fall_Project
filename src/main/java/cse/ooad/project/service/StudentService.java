package cse.ooad.project.service;



import cse.ooad.project.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public boolean verifyValidity(){
        return true;
    }



}

