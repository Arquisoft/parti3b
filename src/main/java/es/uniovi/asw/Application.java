package es.uniovi.asw;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.Banner;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
public class Application {

   public static void main(String[] args) {
    	//SpringApplication.run(Application.class, args);
	    SpringApplication app = new SpringApplication(Application.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
   }
}