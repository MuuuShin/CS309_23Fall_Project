package cse.ooad.project.repository;


import cse.ooad.project.model.Building;
import cse.ooad.project.model.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FloorRepository extends JpaRepository<Floor, Long> {

    Floor getFloorsByBuilding(Building building);

    Floor getFloorByName(String name);



}
