package cse.ooad.project.service;


import cse.ooad.project.model.Region;
import cse.ooad.project.repository.RegionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegionService {

    @Autowired
    RegionRepository regionRepository;


    /**
     * 获取所有Regions
     * @return
     */
    public List<Region> getRegions(){
        return null;
    }

    public void saveRegion(Region region){}

    public void updateRegion(Region region){}
}
