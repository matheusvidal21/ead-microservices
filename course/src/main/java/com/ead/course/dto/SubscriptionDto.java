package com.ead.course.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class SubscriptionDto {

    @NotNull
    private UUID userId;

}
