package ttl.larku.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ttl.larku.domain.Student;
import ttl.larku.service.StudentService;

import javax.websocket.server.PathParam;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/sbdemo/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }



    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getStudent(@PathVariable("id") int id) {
        Student student = studentService.getStudent(id);
        if(student == null) {
            return ResponseEntity.status(404).body("No student with id: " + id);
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        Student newStudent = studentService.createStudent(student);

        //"http://localhost:8080/sbdemo/student/" + newStudent.getId();

        URI newResource = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newStudent.getId())
                .toUri();


        //return ResponseEntity.created(newResource).body(newStudent);
        return ResponseEntity.created(newResource).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") int id) {
        boolean result = studentService.deleteStudent(id);
        if(result) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(404).body("No Student with id: " + id);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateStudent(@RequestBody Student student) {
        boolean result = studentService.updateStudent(student);
        if(result) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(404).body("No Student with id: " + student.getId());
        }
    }

}
