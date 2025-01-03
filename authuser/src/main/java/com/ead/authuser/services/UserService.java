package com.ead.authuser.services;

import com.ead.authuser.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    List<UserModel> findAll();

    Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable);

    Optional<UserModel> findById(UUID id);

    void delete(UserModel userModel);

    UserModel save(UserModel userModel);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    UserModel saveUser(UserModel userModel);

    void deleteUser(UserModel userModel);

    UserModel updateUser(UserModel userModel);

    UserModel updatePassword(UserModel userModel);

    void subscribeInstructor(UUID userId);
}
