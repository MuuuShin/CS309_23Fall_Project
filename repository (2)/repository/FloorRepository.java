package cse.ooad.project.repository;


import cse.ooad.project.model.Building;
import cse.ooad.project.model.Floor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FloorRepository extends JpaRepository<Floor, Long> {

    List<Floor> getFloorsByBuildingId(Long id);

    Floor getFloorByName(String name);


    Integer removeByFloorId(Long id);


}
