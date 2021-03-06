package nl.novi.initializer;

import nl.novi.ManyToManyApplication;
import nl.novi.model.Course;
import nl.novi.model.Student;
import nl.novi.repository.CourseRepository;
import nl.novi.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataLoader implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(ManyToManyApplication.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Student student1 = new Student("Albert");
        Student student2 = new Student("Peter Anema");
        Student student3 = new Student("Tessa");

        Course course1 = new Course("Java");
        Course course2 = new Course("Spring Boot");

        courseRepository.save(course1);
        courseRepository.save(course2);

        student1.addCourse(course1);
        student2.addCourse(course1);
        student2.addCourse(course2);
        student3.addCourse(course2);

        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);

        Optional<Student> optionalStudent = studentRepository.findById(1L);
        Optional<Course> optionalCourse = courseRepository.findById(1L);

        if (optionalStudent.isPresent() && optionalCourse.isPresent()) {
            Student s = optionalStudent.get();
            log.info(s + " follows " + s.getCourses().size() + " courses.");
            Course c = optionalCourse.get();
            log.info(c + " is attended by " + c.getStudents().size() + " students.");
        }
    }

}