package hk.org.ha.kcc.eform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "hk.org.ha.kcc")
public class KwhEformSvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(KwhEformSvcApplication.class, args);
	}

}
