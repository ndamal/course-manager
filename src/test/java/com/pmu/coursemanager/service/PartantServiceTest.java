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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class PartantServiceTest {

    @Mock
    private PartantRepository partantRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private PartantService partantService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddPartant() {
        Course course = new Course();
        course.setId(1L);

        Partant partant = new Partant();
        partant.setNom("Test Partant");

        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(partantRepository.save(any())).thenReturn(partant);

        Partant createdPartant = partantService.addPartant(1L, partant);

        assertNotNull(createdPartant);
        assertEquals("Test Partant", createdPartant.getNom());
    }

    @Test
    void testGetAllPartantsByCourseId() {
        List<Partant> partants = new ArrayList<>();
        partants.add(new Partant());
        partants.add(new Partant());

        when(partantRepository.findByCourseId(anyLong())).thenReturn(partants);

        List<Partant> retrievedPartants = partantService.getAllPartantsByCourseId(1L);

        assertNotNull(retrievedPartants);
        assertEquals(2, retrievedPartants.size());
    }
}
