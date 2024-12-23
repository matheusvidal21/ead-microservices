package com.ead.notification.notification.hex.core.ports;


import com.ead.notification.notification.hex.core.domain.NotificationDomain;
import com.ead.notification.notification.hex.core.domain.PageInfo;
import com.ead.notification.notification.hex.core.domain.enums.NotificationStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationPersistencePort {

    NotificationDomain save(NotificationDomain notificationDomain);
    List<NotificationDomain> findAllByUserIdAndNotificationStatus(UUID userId, NotificationStatus notificationStatus, PageInfo pageInfo);
    Optional<NotificationDomain> findByNotificationIdAndUserId(UUID notificationId, UUID userId);

}
