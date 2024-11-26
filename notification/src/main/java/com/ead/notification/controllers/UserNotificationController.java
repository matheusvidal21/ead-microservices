package com.ead.notification.controllers;

import com.ead.notification.dtos.NotificationDto;
import com.ead.notification.models.NotificationModel;
import com.ead.notification.services.NotificationService;
import jakarta.validation.Valid;
import jakarta.ws.rs.Path;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserNotificationController {

    @Autowired
    private NotificationService notificationService;

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("/users/{userId}/notifications")
    public ResponseEntity<Page<NotificationModel>> getAllNotificationByUser(@PathVariable UUID userId,
                                                                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                                            Authentication authentication) {
        log.debug("Getting all notifications by user: " + userId);
        return ResponseEntity.ok(this.notificationService.findAllNotificationsByUser(userId, pageable));
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @PutMapping("/users/{userId}/notifications/{notificationId}")
    public ResponseEntity<Object> updateNotification(@PathVariable UUID userId,
                                                     @PathVariable UUID notificationId,
                                                     @RequestBody @Valid NotificationDto notificationDto) {
        log.debug("Updating notification: " + notificationId + " by user: " + userId);
        log.info("Updating notification: " + notificationId + " by user: " + userId);
        Optional<NotificationModel> notificationModelOptional = this.notificationService.findByNoficicationAndUser(notificationId, userId);
        if (notificationModelOptional.isEmpty()) {
            log.error("Error: notification not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: notification not found");
        }
        notificationModelOptional.get().setNotificationStatus(notificationDto.getNotificationStatus());
        this.notificationService.saveNotification(notificationModelOptional.get());
        return ResponseEntity.ok(notificationModelOptional.get());
    }

}
