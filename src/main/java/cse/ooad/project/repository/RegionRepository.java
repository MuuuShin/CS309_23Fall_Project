package cse.ooad.project.repository;

import cse.ooad.project.model.Region;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

    List<Region> findAll();

    Integer deleteByRegionId(Long id);

    //Region getRegionByRegionId(Long id);
}