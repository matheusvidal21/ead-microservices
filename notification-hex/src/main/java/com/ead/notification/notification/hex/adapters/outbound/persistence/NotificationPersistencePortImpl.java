package com.ead.notification.notification.hex.adapters.outbound.persistence;

import com.ead.notification.notification.hex.adapters.outbound.persistence.entities.NotificationEntity;
import com.ead.notification.notification.hex.core.domain.NotificationDomain;
import com.ead.notification.notification.hex.core.domain.PageInfo;
import com.ead.notification.notification.hex.core.domain.enums.NotificationStatus;
import com.ead.notification.notification.hex.core.ports.NotificationPersistencePort;
import ma.glasnost.orika.MapperFacade;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class NotificationPersistencePortImpl implements NotificationPersistencePort {

    private final NotificationJpaRepository notificationJpaRepository;
    private final MapperFacade mapperFacade;

    public NotificationPersistencePortImpl(NotificationJpaRepository notificationJpaRepository, MapperFacade mapperFacade) {
        this.notificationJpaRepository = notificationJpaRepository;
        this.mapperFacade = mapperFacade;
    }

    @Override
    public NotificationDomain save(NotificationDomain notificationDomain) {
        NotificationEntity notificationEntity = notificationJpaRepository.save(mapperFacade.map(notificationDomain, NotificationEntity.class));
        return mapperFacade.map(notificationEntity, NotificationDomain.class);
    }

    @Override
    public List<NotificationDomain> findAllByUserIdAndNotificationStatus(UUID userId, NotificationStatus notificationStatus, PageInfo pageInfo) {
        Pageable pageable = PageRequest.of(pageInfo.getPageNumber(), pageInfo.getPageSize());
        return notificationJpaRepository.findAllByUserIdAndNotificationStatus(userId, notificationStatus, pageable).stream().map(entity -> mapperFacade.map(entity, NotificationDomain.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<NotificationDomain> findByNotificationIdAndUserId(UUID notificationId, UUID userId) {
        Optional<NotificationEntity> notificationEntityOptional = notificationJpaRepository.findByNotificationIdAndUserId(notificationId, userId);
        if (notificationEntityOptional.isPresent()) {
            return Optional.of(mapperFacade.map(notificationEntityOptional.get(), NotificationDomain.class));
        } else {
            return Optional.empty();
        }
    }
}
