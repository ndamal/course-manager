package com.pmu.coursemanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CourseEventProducer {

    private static final String TOPIC = "course-events";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendCourseEvent(String message) {
        kafkaTemplate.send(TOPIC, message);
    }
}
