package com.pmu.coursemanager.controller;

import com.pmu.coursemanager.entity.Course;
import com.pmu.coursemanager.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@AllArgsConstructor
@Api(tags = "Courses")
public class CourseController {

    private CourseService courseService;
    // private CourseEventProducer courseEventProducer;

    @ApiOperation(value = "Créer une nouvelle course", response = Course.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Course créée avec succès"),
            @ApiResponse(code = 400, message = "Requête invalide"),
            @ApiResponse(code = 500, message = "Erreur interne du serveur")
    })
    @Transactional
    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course createdCourse = courseService.createCourse(course);
        /* Publish event to Kafka
        courseEventProducer.sendCourseEvent("New course created: " + createdCourse); */
        return ResponseEntity.ok(createdCourse);

    }

    @ApiOperation(value = "Récupérer une course par son identifiant", response = Course.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Course trouvée"),
            @ApiResponse(code = 404, message = "Course non trouvée")
    })
    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long courseId) {
        Course course = courseService.getCourseById(courseId);
        return ResponseEntity.ok(course);
    }

    @ApiOperation(value = "Récupérer la liste des courses par", response = Course.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Course trouvée")
    })
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

}
