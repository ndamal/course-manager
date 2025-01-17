package com.pmu.coursemanager.repository;

import com.pmu.coursemanager.entity.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
    Course findByNumberUnique(Integer numberUnique);

}
