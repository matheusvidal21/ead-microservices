package com.ead.notification.notification.hex.adapters.outbound.persistence;

import com.ead.notification.notification.hex.adapters.outbound.persistence.entities.NotificationEntity;
import com.ead.notification.notification.hex.core.domain.enums.NotificationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface NotificationJpaRepository extends JpaRepository<NotificationEntity, UUID> {

    Page<NotificationEntity> findAllByUserIdAndNotificationStatus(UUID userId, NotificationStatus notificationStatus, Pageable pageable);

    Optional<NotificationEntity> findByNotificationIdAndUserId(UUID notificationId, UUID userId);
}
