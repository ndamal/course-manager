package com.pmu.coursemanager.service;

import com.pmu.coursemanager.entity.Course;
import com.pmu.coursemanager.entity.Partant;
import com.pmu.coursemanager.exception.NotFoundException;
import com.pmu.coursemanager.repository.CourseRepository;
import com.pmu.coursemanager.repository.PartantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class CourseService {

    private CourseRepository courseRepository;
    private PartantRepository partantRepository;

    /**
     * create element=course.
     *
     * @param course element to create
     * @return element after creation
     * @throws IllegalArgumentException   Exception lancée si la liste des partant < 3
     * ou si le le numero de la course n'est unique
     */
    public Course createCourse(Course course) {
        // Validation pour s'assurer qu'une course possède au moins 3 partants
        if (course.getPartants().size() < 3) {
            throw new IllegalArgumentException("Une course doit avoir au moins 3 partants.");
        }
        // Validation pour s'assurer que la numero de la course est unique
        if (courseRepository.findByNumberUnique(course.getNumberUnique()) != null) {
            throw new IllegalArgumentException("Le numéro unique de la course est en double");
        }
        course.setDate(LocalDate.now());
        Course createdCourse = courseRepository.save(course);
        log.info(String.format("Nouvelle course créée => %s ", createdCourse.getId()));
        for (int j = 0; j < course.getPartants().size(); j++) {
            Partant partant = course.getPartants().get(j);
            partant.setNumero(j + 1); // Numéro unique à partir de 1
            partant.setCourse(createdCourse);
            partantRepository.save(partant);
        }

        return createdCourse;
    }

    /**
     * @param courseId element ID
     * @return element
     * @throws NotFoundException Exception si l'element n'existe pas
     */
    public Course getCourseById(Long courseId) {
        // Logique pour récupérer une course par son identifiant
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found with id: " + courseId));
    }

    /**
     * get elements.
     *
     * @return elements
     */
    public List<Course> getAllCourses() {
        return (List<Course>) courseRepository.findAll();
    }

}

