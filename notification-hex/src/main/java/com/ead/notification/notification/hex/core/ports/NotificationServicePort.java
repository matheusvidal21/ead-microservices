package com.ead.notification.notification.hex.core.ports;
import com.ead.notification.notification.hex.core.domain.NotificationDomain;
import com.ead.notification.notification.hex.core.domain.PageInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationServicePort {

    NotificationDomain saveNotification(NotificationDomain notificationDomain);
    List<NotificationDomain> findAllNotificationsByUser(UUID userId, PageInfo pageable);
    Optional<NotificationDomain> findByNotificationIdAndUserId(UUID notificationId, UUID userId);
}
