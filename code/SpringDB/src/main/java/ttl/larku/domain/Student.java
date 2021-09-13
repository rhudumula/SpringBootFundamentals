package ttl.larku.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQuery(name = "Student.smallSelect", query = "Select s from Student s")
@NamedQuery(name = "Student.bigSelect", query = "select distinct s from Student s left join fetch s.classes sc left join fetch sc.course")
@NamedQuery(name = "Student.bigSelectOne", query = "select distinct s from Student s left join fetch s.classes "
		+ "sc left join fetch sc.course where s.id = :id")
@NamedQuery(name = "Student.getByName", query = "select distinct s from Student s left join fetch s.classes "
        + "sc left join fetch sc.course where s.name like CONCAT('%',:name,'%')")
public class Student {

    public enum Status {
        FULL_TIME, PART_TIME, HIBERNATING
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

//    @Column(name = "PHONENUMBER")
    private String phoneNumber;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dob=" + dob +
                ", status=" + status +
                ", classes=" + classes +
                '}';
    }

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    private Status status = Status.FULL_TIME;

    @JacksonXmlElementWrapper(localName = "classes")
    @JacksonXmlProperty(localName = "class")
    @ManyToMany (fetch = FetchType.LAZY )
    @Cascade({CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "Student_ScheduledClass",
            joinColumns = @JoinColumn(name = "students_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "classes_id", referencedColumnName = "id"),
            uniqueConstraints = {
                    @UniqueConstraint(columnNames = {"students_id", "classes_id"})})
    private List<ScheduledClass> classes;


    public Student() {
        this("Unknown");
    }

    public Student(String name) {
        this(name, null, null, Status.FULL_TIME, new ArrayList<ScheduledClass>());
    }

//    public Student(String name, String phoneNumber, Status status) {
//        this(name, phoneNumber, null, status, new ArrayList<ScheduledClass>());
//    }

    public Student(String name, String phoneNumber, LocalDate dob, Status status) {
        this(name, phoneNumber, dob, status, new ArrayList<ScheduledClass>());
    }

    public Student(String name, String phoneNumber, LocalDate dob, Status status, List<ScheduledClass> classes) {
        super();
        this.name = name;
        this.status = status;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.classes = classes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public Status[] getStatusList() {
        return Status.values();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public List<ScheduledClass> getClasses() {
        return classes;
    }

    public void setClasses(List<ScheduledClass> classes) {
        this.classes = classes;
    }

    public void addClass(ScheduledClass sClass) {
        classes.add(sClass);
    }

    public void dropClass(ScheduledClass sClass) {
        classes.remove(sClass);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && name.equals(student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
