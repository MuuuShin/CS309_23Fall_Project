package cse.ooad.project.repository;

import cse.ooad.project.model.Group;
import java.awt.print.Pageable;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {



    void deleteByGroupId(Long id);


    //@Cacheable("provinces")
    Group getGroupByGroupId(Long id);

    // findAll(Pageable pageable);

    Group getGroupByRoomId(Long id);


    @Query("SELECT g FROM Group g JOIN FETCH g.memberList")
    List<Group> findAllWithMembers();

    List<Group> findGroupsByIntroContainingOrNameContaining(String intro, String name);


}