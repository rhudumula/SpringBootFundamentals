package ttl.larku;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import ttl.larku.domain.Student;
import ttl.larku.service.StudentService;

import java.util.List;

//@EnableAutoConfiguration
//@ComponentScan
//@Configuration
@SpringBootApplication
public class SbdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbdemoApplication.class, args);
    }
}

@Component
class MyRunner implements CommandLineRunner
{

    private final StudentService studentService;

    public MyRunner(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Here we go with Spring Boot");

        List<Student> students = studentService.getAllStudents();

        System.out.println("students.size: " + students.size());
        students.forEach(System.out::println);
    }
}