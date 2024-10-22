package cse.ooad.project.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import cse.ooad.project.model.Group;
import cse.ooad.project.model.Room;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> getRoomsByFloorId(Long id);

    Room getRoomsByRoomId(Long id);

    Integer deleteByRoomId(Long id);

    interface GroupStarListProjection {
        Group getGroupStarList();
    }


    List<GroupStarListProjection> getGroupStarListByRoomId(Long id);

    List<Room> findAllByFloorIdIn(List<Long> floorIds);


    @Query("SELECT r FROM Room r JOIN FETCH r.groupStarList WHERE r.floorId IN :ids")
    List<Room> findAllWithGroupStarListByBuildingIds(@Param("ids") List<Long> ids);

//    GroupStarListProjection1 getGroupByRoomId(Long id);

    @Transactional
    @Modifying
    @Query(value = "SELECT insert_room_data(:nameList, :typeList, :introList, :floorNameList, :buildingNameList, :regionNameList);", nativeQuery = true)
    List<Object> batchInsertRoomDataFunction(
            @Param("nameList") String[] nameList,
            @Param("typeList") Integer[] typeList,
            @Param("introList") String[] introList,
            @Param("floorNameList") String[] floorNameList,
            @Param("buildingNameList") String[] buildingNameList,
            @Param("regionNameList") String[] regionNameList
    );

}
