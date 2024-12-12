package com.ead.course.controllers;

import com.ead.course.dto.SubscriptionDto;
import com.ead.course.enums.UserStatus;
import com.ead.course.models.CourseModel;
import com.ead.course.models.UserModel;
import com.ead.course.services.CourseService;
import com.ead.course.services.UserService;
import com.ead.course.specifications.SpecificationTemplate;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class CourseUserController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Object> getAllUsersByCourse(SpecificationTemplate.UserSpec spec,
                                                      @PathVariable UUID courseId,
                                                      @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Optional<CourseModel> course = this.courseService.findById(courseId);
        if (course.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }

        return ResponseEntity.ok(this.userService.findAll(SpecificationTemplate.userCourseId(courseId).and(spec), pageable));
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @PostMapping("/courses/{courseId}/users/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable UUID courseId,
                                                               @RequestBody @Valid SubscriptionDto subscriptionDto){
        Optional<CourseModel> courseModelOptional = this.courseService.findById(courseId);
        if (courseModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: course not found");
        }

        if (this.courseService.existsByCourseAndUser(courseId, subscriptionDto.getUserId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: user already subscribed to the course");
        }

        Optional<UserModel> userModelOptional = this.userService.findById(subscriptionDto.getUserId());
        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: user not found");
        }

        if (userModelOptional.get().getUserStatus().equals(UserStatus.BLOCKED.toString())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: user is blocked");
        }

        this.courseService.saveSubscriptionUserInCourseAndSendNotification(courseModelOptional.get(), userModelOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body("Subscription created successfully");
    }
}
