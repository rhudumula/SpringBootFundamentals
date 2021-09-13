package ttl.larku.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ttl.larku.dao.repository.StudentRepo;
import ttl.larku.domain.Student;
import ttl.larku.domain.Student.Status;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class StudentRepoService implements StudentService {

    @Autowired
//    private SimpleStudentRepo studentDAO;
    private StudentRepo studentDAO;

    @Override
    public Student createStudent(String name) {
        Student student = new Student(name);
        student = studentDAO.save(student);

        return student;
    }

    @Override
    public Student createStudent(String name, String phoneNumber, LocalDate dob, Status status) {
        return createStudent(new Student(name, phoneNumber, dob, status));
    }

    @Override
    public Student createStudent(Student student) {
        student = studentDAO.save(student);

        return student;
    }

    @Override
    public boolean deleteStudent(int id) {
        Student student = studentDAO.findById(id).orElse(null);
        if(student != null) {
            studentDAO.delete(student);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateStudent(Student newStudent) {
        Student oldStudent = studentDAO.findById(newStudent.getId()).orElse(null);
        if(oldStudent != null) {
            studentDAO.save(newStudent);
            return true;
        }
        return false;
    }

    @Override
    public Student getStudent(int id) {
        return studentDAO.findById(id).orElse(null);
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = studentDAO.findAll();
        return students;
    }

    @Override
    public List<Student> getByName(String name) {
        String lc = name.toLowerCase();
        List<Student> result = studentDAO.findByNameIgnoreCaseContains(name);
        return result;
    }

//    public SimpleStudentRepo getStudentDAO() {
//        return studentDAO;
//    }
//
//    public void setStudentDAO(SimpleStudentRepo studentDAO) {
//        this.studentDAO = studentDAO;
//    }

    @Override
    public void clear() {
    }

}
