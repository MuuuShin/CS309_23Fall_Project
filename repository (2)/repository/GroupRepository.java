package cse.ooad.project.repository;

import cse.ooad.project.model.Group;
import java.awt.print.Pageable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {



    void deleteByGroupId(Long id);



    Group getGroupByGroupId(Long id);

    // findAll(Pageable pageable);

    Group getGroupByRoomId(Long id);





}