package ttl.larku.dao;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import ttl.larku.dao.repository.ClassRepo;
import ttl.larku.domain.Course;
import ttl.larku.domain.ScheduledClass;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DataJpaTest
//Populate your DB.  From Most Expensive to least expensive

//This will make recreate the context after every test.
//In conjunction with appropriate 'schema[-XXX].sql' and 'data[-XXX].sql' files
//it will also drop and recreate the DB before each test.
//@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)

//Or you can just re-run the sql files before each test method
//@Sql(scripts = { "/ttl/larku/db/createDB-h2.sql", "/ttl/larku/db/populateDB-h2.sql" }, executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)

//This next one will roll back the transaction after
//each test, so the database will actually stay the
//same for the next test.
@Transactional
public class ClassRepoTest {

    private String name1 = "Bloke";
    private String name2 = "Blokess";
    private String newName = "Different Bloke";

    private String code1 = "BOT-101";
    private String code2 = "BOT-202";
    private String title1 = "Intro To Botany";
    private String title2 = "Advanced Basket Weaving";

    private LocalDate startDate1 = LocalDate.parse("2012-10-10");
    private LocalDate startDate2 = LocalDate.parse("2013-10-10");
    private LocalDate endDate1 = LocalDate.parse("2013-05-10");
    private LocalDate endDate2 = LocalDate.parse("2014-05-10");

    private Course course1;
    private Course course2;
    private ScheduledClass class1;
    private ScheduledClass class2;

    @Resource(name = "classRepo")
    private ClassRepo dao;


    @BeforeEach
    public void setup() {
        course1 = new Course(code1, title1);
        course2 = new Course(code2, title2);

        class1 = new ScheduledClass(course1, startDate1, endDate1);
        class2 = new ScheduledClass(course2, startDate2, endDate2);
    }

    @Test
    public void testGetAll() {
        List<ScheduledClass> classes = dao.findAll();
        assertEquals(3, classes.size());
        System.out.println(classes);
    }

    @Test
    public void testGetStudentsByCourseCodeAndStartDate() {
        List<ScheduledClass> classes = dao.getByCourseCodeAndStartDateForStudents("BOT-202", LocalDate.parse("2012-10-10"));

        int newId = dao.save(class1).getId();

        ScheduledClass result = dao.findById(newId).orElse(null);

        assertEquals(newId, result.getId());
    }

    @Test
    public void testUpdate() {
        int newId = dao.save(class1).getId();

        ScheduledClass result = dao.findById(newId).orElse(null);

        assertEquals(newId, result.getId());

        result.setCourse(course2);
        dao.save(result);

        result = dao.findById(newId).orElse(null);
        assertEquals(title2, result.getCourse().getTitle());
    }

    @Test
    public void testDelete() {
        int id1 = dao.save(class1).getId();

        ScheduledClass resultClass = dao.findById(id1).orElse(null);
        assertEquals(resultClass.getId(), id1);

        dao.delete(resultClass);

        resultClass = dao.findById(id1).orElse(null);

        assertEquals(null, resultClass);
    }
}