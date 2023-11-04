package cse.ooad.project.repository;

import cse.ooad.project.model.Room;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> getRoomsByFloorId(Long id);
}
