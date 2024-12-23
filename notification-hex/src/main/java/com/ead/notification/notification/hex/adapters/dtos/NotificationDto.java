package com.ead.notification.notification.hex.adapters.dtos;
import com.ead.notification.notification.hex.core.domain.enums.NotificationStatus;
import jakarta.validation.constraints.NotNull;

public class NotificationDto {

    @NotNull
    private NotificationStatus notificationStatus;

    public NotificationStatus getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(NotificationStatus notificationStatus) {
        this.notificationStatus = notificationStatus;
    }
}
