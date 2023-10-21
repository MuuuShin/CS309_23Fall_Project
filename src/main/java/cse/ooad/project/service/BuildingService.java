package cse.ooad.project.service;


import cse.ooad.project.model.Region;
import cse.ooad.project.repository.BuildingRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.stereotype.Service;

@Service
public class BuildingService {


    @Autowired
    BuildingRepository buildingRepository;
    public void SaveBuilding(Build build){

    }

    public void changeBuilding(Build build){}

    public void deleteBuilding(Build build){}

    public List<Build> getBuildsByRegion(Region region){
        return null;
    }


}
