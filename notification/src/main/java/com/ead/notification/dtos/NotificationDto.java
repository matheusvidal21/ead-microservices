package com.ead.notification.dtos;

import com.ead.notification.enums.NotificationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificationDto {

    @NotNull
    private NotificationStatus notificationStatus;

}
