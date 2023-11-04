package cse.ooad.project.repository;

import cse.ooad.project.model.Student;
import cse.ooad.project.service.StudentService;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student getStudentByAccount(String account);

    Student getStudentByStudentId(Long studentId);

    List<Student> getStudentsByName(String name);

    List<Student> getStudentsBySleepTimeLessThanAndAwakeTimeGreaterThanAndIntroLikeAAndGenderAAndType(
        Time sleepTime, Time awakeTime, String intro,Long gender, Long type);



}