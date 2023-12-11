package cse.ooad.project.repository;


import cse.ooad.project.model.Building;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {

    List<Building> getBuildingsByRegionId(Long regionId);

    Building getBuildingByBuildingId(Long buildingId);

    Long removeByBuildingId(Long BuildingId);

}
