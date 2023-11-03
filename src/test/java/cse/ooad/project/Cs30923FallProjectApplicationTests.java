package cse.ooad.project;

import cse.ooad.project.model.Building;
import cse.ooad.project.repository.BuildingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Cs30923FallProjectApplicationTests {
	@Autowired
	BuildingRepository buildingRepository;
	@Test
	void contextLoads() {
		Building build = new Building();
		build.setName("国家安全局");
		buildingRepository.save(build);

	}

}
