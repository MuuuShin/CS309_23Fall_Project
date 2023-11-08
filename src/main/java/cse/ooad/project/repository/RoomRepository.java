package cse.ooad.project.repository;

import cse.ooad.project.model.Group;
import cse.ooad.project.model.Room;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> getRoomsByFloorId(Long id);

    Room getRoomsByRoomId(Long id);

    Integer deleteByRoomId(Long id);

    interface GroupStarListProjection {
        Group getGroupStarList();

    }
//    todo 对应的代码
//    interface GroupStarListProjection1 {
//        List<Group> getGroupStarList();
//
//    }

    List<GroupStarListProjection> getGroupStarListByRoomId(Long id);

//    GroupStarListProjection1 getGroupByRoomId(Long id);



}
