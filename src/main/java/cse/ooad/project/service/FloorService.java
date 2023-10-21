package cse.ooad.project.service;


import cse.ooad.project.model.Building;
import cse.ooad.project.model.Floor;
import cse.ooad.project.repository.FloorRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FloorService {

    @Autowired
    FloorRepository floorRepository;


    public void saveFloor(Floor floor){}

    public void deleteFloor(Floor floor){

    }

    public void updateFloor(Floor floor){}

    public List<Floor> getFloorsByBuilding(Building building){
        return null;
    }

    public Floor gerFloorByFloorName(String name){
        return null;
    }





}
