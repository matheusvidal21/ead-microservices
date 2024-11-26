package com.ead.course.controllers;

import com.ead.course.dto.CourseDto;
import com.ead.course.models.CourseModel;
import com.ead.course.services.CourseService;
import com.ead.course.specifications.SpecificationTemplate;
import com.ead.course.validation.CourseValidator;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseValidator courseValidator;

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @PostMapping
    public ResponseEntity<Object> saveCourse(@RequestBody CourseDto courseDto, Errors errors){
        log.debug("POST saveCourse courseDto: {}", courseDto.toString());
        this.courseValidator.validate(courseDto, errors);
        if (errors.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
        }
        var courseModel = new CourseModel();
        BeanUtils.copyProperties(courseDto, courseModel);
        courseModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        courseModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        log.debug("POST saveCourse courseModel: {}", courseModel.getId());
        log.info("Course saved successfully courseId {}", courseModel.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(this.courseService.save(courseModel));
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @DeleteMapping("/{courseId}")
    public ResponseEntity<Object> deleteCourse(@PathVariable UUID courseId){
        log.debug("DELETE deleteCourse courseId: {}", courseId);
        Optional<CourseModel> course = this.courseService.findById(courseId);
        if (course.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
        this.courseService.delete(course.get());

        log.info("Course deleted successfully courseId {}", courseId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Course deleted successfully");
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @PutMapping("/{courseId}")
    public ResponseEntity<Object> updateCourse(@PathVariable UUID courseId, @RequestBody @Valid CourseDto courseDto){
        log.debug("PUT updateCourse courseId: {}, courseDto: {}", courseId, courseDto.toString());
        Optional<CourseModel> courseModelOptional = this.courseService.findById(courseId);
        if (courseModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
        var courseModel = courseModelOptional.get();
        courseModel.setName(courseDto.getName());
        courseModel.setImageUrl(courseDto.getImageUrl());
        courseModel.setCourseLevel(courseDto.getCourseLevel());
        courseModel.setCourseStatus(courseDto.getCourseStatus());
        courseModel.setDescription(courseDto.getDescription());
        courseModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        log.debug("PUT updateCourse courseModel: {}", courseModel.getId());
        log.info("Course updated successfully courseId {}", courseModel.getId());
        return ResponseEntity.status(HttpStatus.OK).body(this.courseService.save(courseModel));
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping
    public ResponseEntity<Page<CourseModel>> getAll(SpecificationTemplate.CourseSpec spec,
                                                    @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                    @RequestParam(required = false) UUID userId){
        log.debug("GET getAllCourses spec {}, pageable {}", spec, pageable);

        if (userId != null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(this.courseService.findAll(SpecificationTemplate.courseUserId(userId).and(spec), pageable));
        }
        return ResponseEntity.status(HttpStatus.OK).body(courseService.findAll(spec, pageable));
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("/{courseId}")
    public ResponseEntity<Object> getOneCourse(@PathVariable UUID courseId){
        log.debug("GET getOneCourse courseId: {}", courseId);
        Optional<CourseModel> course = this.courseService.findById(courseId);
        if (course.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(course.get());
    }
}
