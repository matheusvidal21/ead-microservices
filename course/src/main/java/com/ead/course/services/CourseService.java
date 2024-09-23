package com.ead.course.services;

import com.ead.course.models.CourseModel;
import com.ead.course.specifications.SpecificationTemplate;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseService {

    void delete(CourseModel course);

    CourseModel save(CourseModel courseModel);

    Optional<CourseModel> findById(UUID id);

    List<CourseModel> findAll();

    Page<CourseModel> findAll(Specification<CourseModel> spec, Pageable pageable);

    boolean existsByCourseAndUser(UUID courseId, UUID userId);

    void saveSubscriptionUserInCourse(UUID courseId, UUID userId);
}
