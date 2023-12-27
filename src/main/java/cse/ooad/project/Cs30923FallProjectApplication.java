package cse.ooad.project;

import cse.ooad.project.utils.DynamicTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;



@EnableCaching
@SpringBootApplication
public class Cs30923FallProjectApplication implements CommandLineRunner {
	@Autowired
	DynamicTask dynamicTask;
	public static void main(String[] args) {
		SpringApplication.run(Cs30923FallProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		dynamicTask.startCron();
	}
}
