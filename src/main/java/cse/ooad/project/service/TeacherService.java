package cse.ooad.project.service;


import cse.ooad.project.model.Room;
import cse.ooad.project.model.Student;
import cse.ooad.project.model.Teacher;
import cse.ooad.project.repository.TeacherRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
    @Autowired
    TeacherRepository teacherRepository;


    public void saveStudentList(List<Student> students){

    }

    public void saveStudent(Student student){

    }

    public void deleteStudent(Student student){}

    public void updateStudent(Student student){}

    public void saveRoom(Teacher teacher, Room room){}

    public void updateRoom(Room room){}

    public void updateSelectRoomTime(){}


    /**
     * 获取宿舍选择情况
     */
    public void getDormitorySelection(){}
}
