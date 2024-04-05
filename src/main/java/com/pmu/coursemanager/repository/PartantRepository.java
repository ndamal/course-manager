package com.pmu.coursemanager.repository;

import com.pmu.coursemanager.entity.Partant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartantRepository extends CrudRepository<Partant, Long> {
    List<Partant> findByCourseId(Long courseId);
}
