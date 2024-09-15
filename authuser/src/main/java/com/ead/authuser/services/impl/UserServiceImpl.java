package com.ead.authuser.services.impl;

import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.models.UserCourseModel;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserCourseRepository;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCourseRepository userCourseRepository;

    @Autowired
    private CourseClient courseClient;

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
    public void delete(UUID id) {
        List<UserCourseModel> userCourseModelList = this.userCourseRepository.findAllUserCourseIntoUser(id);
        if (!userCourseModelList.isEmpty()){
            this.userCourseRepository.deleteAll(userCourseModelList);
        }

        this.userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void delete(UserModel userModel){
        boolean deleteUserCourseInCourse = false;
        List<UserCourseModel> userCourseModelList = this.userCourseRepository.findAllUserCourseIntoUser(userModel.getId());
        if (!userCourseModelList.isEmpty()){
            this.userCourseRepository.deleteAll(userCourseModelList);
            deleteUserCourseInCourse = true;
        }

        this.userRepository.delete(userModel);
        if (deleteUserCourseInCourse){
            this.courseClient.deleteUserInCourse(userModel.getId());
        }
    }


    @Override
    public void save(UserModel userModel) {
        this.userRepository.save(userModel);
    }

    @Override
    public boolean existsByUsername(String username) {
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }
}
