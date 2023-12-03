package cse.ooad.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class Cs30923FallProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(Cs30923FallProjectApplication.class, args);
	}
}
