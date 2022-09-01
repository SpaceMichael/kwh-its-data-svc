package hk.org.ha.kcc.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "hk.org.ha.kcc")
public class KccSpringBootSvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(KccSpringBootSvcApplication.class, args);
	}

}
