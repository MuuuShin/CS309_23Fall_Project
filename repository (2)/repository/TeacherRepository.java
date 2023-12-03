package cse.ooad.project.repository;

import cse.ooad.project.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Teacher getTeacherByAccount(String account);

    Teacher getTeacherByTeacherId(Long id);
}