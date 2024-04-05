package com.pmu.coursemanager.service;

import com.pmu.coursemanager.entity.Course;
import com.pmu.coursemanager.entity.Partant;
import com.pmu.coursemanager.exception.NotFoundException;
import com.pmu.coursemanager.repository.CourseRepository;
import com.pmu.coursemanager.repository.PartantRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PartantService {

    private PartantRepository partantRepository;

    private CourseRepository courseRepository;

    /**
     * create element=partant.
     *
     * @param courseId course ID
     * @param partant  element to add
     * @return element afyer creation
     */
    public Partant addPartant(Long courseId, Partant partant) {
        // Récupérer la course par son identifiant
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found with id: " + courseId));

        // Valider le numéro unique du partant dans la course
        validatePartantNumero(course, partant.getNumero());

        // Assigner la course au partant
        partant.setCourse(course);
        partant.setNumero(course.getPartants().size() + 1);

        // Sauvegarder le partant
        return partantRepository.save(partant);
    }

    /**
     * Verify element number
     *
     * @param course Course
     * @param numero Partant number
     * @throws IllegalArgumentException Exception lancée si le numero du partant existe dans la course
     */
    private void validatePartantNumero(Course course, int numero) {
        // Valider que le numéro du partant n'existe pas déjà dans la course
        for (Partant existingPartant : course.getPartants()) {
            if (existingPartant.getNumero() == numero) {
                throw new IllegalArgumentException("Le numéro du partant existe déjà dans la course.");
            }
        }
    }

    /**
     * Récupérer tous les partants d'une course spécifique
     *
     * @param courseId IDCourse
     * @return list partant of the course
     */
    public List<Partant> getAllPartantsByCourseId(Long courseId) {
        return partantRepository.findByCourseId(courseId);
    }

}


