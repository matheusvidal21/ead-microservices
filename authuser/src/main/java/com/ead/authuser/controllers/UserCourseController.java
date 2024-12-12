package com.ead.authuser.controllers;

import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
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
public class UserCourseController {

    @Autowired
    private CourseClient courseClient;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<Object> getAllCoursesByUser(@PathVariable UUID userId,
                                                      @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                      @RequestHeader("Authorization") String token) {
        Optional<UserModel> userModelOptional = this.userService.findById(userId);
        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        return ResponseEntity.ok(this.courseClient.getAllCoursesByUser(userId, token, pageable));
    }

}
