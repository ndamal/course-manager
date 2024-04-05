package com.pmu.coursemanager.service;

import com.pmu.coursemanager.entity.Course;
import com.pmu.coursemanager.entity.Partant;
import com.pmu.coursemanager.repository.CourseRepository;
import com.pmu.coursemanager.repository.PartantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private PartantRepository partantRepository;

    @InjectMocks
    private CourseService courseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateCourseWithEnoughPartants() {
        Course course = new Course();
        course.setName("Test Course");
        course.setNumberUnique(123);

        // Ajouter au moins trois partants à la course
        Partant partant1 = new Partant();
        Partant partant2 = new Partant();
        Partant partant3 = new Partant();
        course.getPartants().add(partant1);
        course.getPartants().add(partant2);
        course.getPartants().add(partant3);

        when(courseRepository.findByNumberUnique(anyInt())).thenReturn(null);
        when(courseRepository.save(any())).thenReturn(course);

        Course createdCourse = courseService.createCourse(course);

        assertNotNull(createdCourse);
        assertEquals("Test Course", createdCourse.getName());
        assertEquals(123, createdCourse.getNumberUnique());
    }

    @Test
    void testCreateCourseWithLessThanThreePartants() {
        Course course = new Course();
        course.setName("Test Course");
        course.setNumberUnique(123);

        // Ajouter moins de trois partants à la course
        Partant partant1 = new Partant();
        course.getPartants().add(partant1);

        when(courseRepository.findByNumberUnique(anyInt())).thenReturn(null);
        when(courseRepository.save(any())).thenReturn(course);

        // Vérifier que la création de course échoue avec moins de trois partants
        assertThrows(IllegalArgumentException.class, () -> {
            courseService.createCourse(course);
        });
    }


    @Test
    void testGetCourseById() {
        Course course = new Course();
        course.setId(1L);

        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));

        Course retrievedCourse = courseService.getCourseById(1L);

        assertNotNull(retrievedCourse);
        assertEquals(1L, retrievedCourse.getId());
    }

    @Test
    void testGetAllCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course());
        courses.add(new Course());

        when(courseRepository.findAll()).thenReturn(courses);

        List<Course> retrievedCourses = courseService.getAllCourses();

        assertNotNull(retrievedCourses);
        assertEquals(2, retrievedCourses.size());
    }
}
