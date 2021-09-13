package ttl.larku.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ttl.larku.domain.Course;
import ttl.larku.domain.Student;
import ttl.larku.jconfig.LarkUConfig;
import ttl.larku.service.CourseService;
import ttl.larku.service.StudentService;

import java.util.List;

public class SpringDemo {

    public static void main(String[] args) {

        //ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ApplicationContext context = new AnnotationConfigApplicationContext(LarkUConfig.class);

        StudentService service = context.getBean("studentService", StudentService.class);

        List<Student> students = service.getAllStudents();
        System.out.println("student.size: " + students.size());
        students.forEach(System.out::println);

        CourseService courseService= context.getBean("courseService", CourseService.class);

        List<Course> courses = courseService.getAllCourses();
        System.out.println("courses.size: " + courses.size());
        courses.forEach(System.out::println);
    }
}
