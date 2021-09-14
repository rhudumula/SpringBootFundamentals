package ttl.larku.sbdemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class SbdemoApplication {

    public static void main(String[] args) {
        System.out.println("Main called");
        SpringApplication.run(SbdemoApplication.class, args);
    }

}

@Component
class MyRunner implements CommandLineRunner
{

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Here we go with Spring Boot");
    }
}