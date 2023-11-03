package cse.ooad.project.repository;


import cse.ooad.project.model.Building;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {

    List<Building> findBuildingsByRegionId(Long regionId);

    Building save(Building building);

    void removeByBuildingId(Long BuildingId);

}
