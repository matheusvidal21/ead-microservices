package com.ead.course.controllers;

import com.ead.course.clients.CourseClient;
import com.ead.course.dto.UserDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class CourseUserController {

    @Autowired
    private CourseClient courseClient;

    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Page<UserDto>> getAllUsersByCourse(@PathVariable UUID courseId,
                                                             @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(this.courseClient.getAllUsersByCourse(courseId, pageable));
    }
}
