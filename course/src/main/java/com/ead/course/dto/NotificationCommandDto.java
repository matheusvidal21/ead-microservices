package com.ead.course.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class NotificationCommandDto {

    private String title;

    private String message;

    private UUID userId;

}
