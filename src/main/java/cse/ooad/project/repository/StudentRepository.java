package cse.ooad.project.repository;

import cse.ooad.project.model.Student;
import java.sql.Time;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student getStudentByAccount(String account);

    Student getStudentByStudentId(Long studentId);

    List<Student> getStudentsByName(String name);

    Integer deleteByStudentId(Long id);

    List<Student> getStudentsByIntroContainingAndGenderAndType(String intro,Long gender, Long type);

    Collection<Student> getStudentsByIntroContainingOrAccountContainingOrNameContaining(String intro, String account, String name);

}