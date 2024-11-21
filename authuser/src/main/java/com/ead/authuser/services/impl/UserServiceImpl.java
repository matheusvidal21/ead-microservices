package com.ead.authuser.services.impl;

import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.enums.ActionType;
import com.ead.authuser.enums.RoleType;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.RoleModel;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.publishers.UserEventPublisher;
import com.ead.authuser.repositories.RoleRepository;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CourseClient courseClient;

    @Autowired
    private UserEventPublisher userEventPublisher;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserModel> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable) {
        return this.userRepository.findAll(spec, pageable);
    }

    @Override
    public Optional<UserModel> findById(UUID id) {
        return this.userRepository.findById(id);
    }

    @Transactional
    @Override
    public void delete(UserModel userModel){
        this.userRepository.delete(userModel);
    }

    @Override
    public UserModel save(UserModel userModel) {
        userModel.setPassword(this.passwordEncoder.encode(userModel.getPassword()));
        return this.userRepository.save(userModel);
    }

    @Override
    public boolean existsByUsername(String username) {
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    @Transactional
    @Override
    public UserModel saveUser(UserModel userModel) {
        userModel = this.save(userModel);
        this.userEventPublisher.publishUserEvent(userModel.convertToUserEventDto(), ActionType.CREATE);
        return userModel;
    }

    @Transactional
    @Override
    public void deleteUser(UserModel userModel) {
        this.delete(userModel);
        this.userEventPublisher.publishUserEvent(userModel.convertToUserEventDto(), ActionType.DELETE);
    }

    @Override
    public UserModel updateUser(UserModel userModel) {
        userModel = this.save(userModel);
        this.userEventPublisher.publishUserEvent(userModel.convertToUserEventDto(), ActionType.UPDATE);
        return userModel;
    }

    @Override
    public UserModel updatePassword(UserModel userModel) {
        userModel.setPassword(this.passwordEncoder.encode(userModel.getPassword()));
        return this.save(userModel);
    }

    @Override
    @Transactional
    public void subscribeInstructor(UUID userId) {
        RoleModel roleModel = this.roleRepository.findByRoleName(RoleType.ROLE_INSTRUCTOR).orElseThrow(() -> new RuntimeException("Role not found"));
        this.userRepository.addRoleToUser(userId, roleModel.getId());
        this.userRepository.updateUserType(userId, UserType.INSTRUCTOR.toString());
    }
}
