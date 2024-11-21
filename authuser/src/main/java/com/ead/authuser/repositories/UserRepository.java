package com.ead.authuser.repositories;

import com.ead.authuser.models.UserModel;
import org.springframework.data.jpa.repository.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID>, JpaSpecificationExecutor<UserModel> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = {"roles"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<UserModel> findByUsername(String username);

    @Modifying
    @Query(
            value = "UPDATE tb_users SET user_type = :userType WHERE user_id = :userId",
            nativeQuery = true
    )
    void updateUserType(UUID userId, String userType);

    @Modifying
    @Query(
            value = "INSERT INTO tb_users_roles (user_id, role_id) VALUES (:userId, :roleId)",
            nativeQuery = true
    )
    void addRoleToUser(UUID userId, UUID roleId);
}
