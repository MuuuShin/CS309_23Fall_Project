package cse.ooad.project;

import cse.ooad.project.model.Building;
import cse.ooad.project.repository.BuildingRepository;
import java.util.HashMap;
import java.util.HashSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Cs30923FallProjectApplicationTests {
	@Autowired
	BuildingRepository buildingRepository;
	@Test
	void contextLoads() {
		HashMap<String , String>  a = new HashMap<>();
		System.out.println(a.get("4"));
	}

}
