package cse.ooad.project.repository;

import cse.ooad.project.model.Region;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

    List<Region> getRegions();
}