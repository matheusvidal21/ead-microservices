package com.ead.course.services.impl;

import com.ead.course.dto.NotificationCommandDto;
import com.ead.course.models.CourseModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.models.UserModel;
import com.ead.course.publishers.NotificationCommandPublisher;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.repositories.UserRepository;
import com.ead.course.services.CourseService;
import jakarta.transaction.Transactional;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private NotificationCommandPublisher notificationCommandPublisher;

    @Transactional
    @Override
    public void delete(CourseModel course) {
        List<ModuleModel> modules = this.moduleRepository.findAllModulesIntoCourse(course.getId());
        if (!modules.isEmpty()){
            for(ModuleModel module : modules){
                List<LessonModel> lessons = this.lessonRepository.findAllLessonsIntoModule(module.getId());
                if (!lessons.isEmpty()){
                    this.lessonRepository.deleteAll(lessons);
                }
            }
            this.moduleRepository.deleteAll(modules);
        }
        this.courseRepository.deleteCourseUserByCourse(course.getId());
        this.courseRepository.delete(course);
    }

    @Override
    public CourseModel save(CourseModel courseModel) {
        return this.courseRepository.save(courseModel);
    }

    @Override
    public Optional<CourseModel> findById(UUID id) {
        return this.courseRepository.findById(id);
    }

    @Override
    public List<CourseModel> findAll() {
        return this.courseRepository.findAll();
    }

    @Override
    public Page<CourseModel> findAll(Specification<CourseModel> spec, Pageable pageable) {
        return this.courseRepository.findAll(spec, pageable);
    }

    @Override
    public boolean existsByCourseAndUser(UUID courseId, UUID userId) {
        return this.courseRepository.existsByCourseAndUser(courseId, userId);
    }

    @Transactional
    @Override
    public void saveSubscriptionUserInCourse(UUID courseId, UUID userId) {
        this.courseRepository.saveSubscriptionUserInCourse(courseId, userId);
    }

    @Transactional
    @Override
    public void saveSubscriptionUserInCourseAndSendNotification(CourseModel courseModel, UserModel userModel) {
        this.courseRepository.saveSubscriptionUserInCourse(courseModel.getId(), userModel.getId());
        try {
            NotificationCommandDto notificationCommandDto = new NotificationCommandDto();
            notificationCommandDto.setUserId(userModel.getId());
            notificationCommandDto.setTitle("Welcome to the course: " + courseModel.getName());
            notificationCommandDto.setMessage(userModel.getFullName() + ", your registration has been successful!");
            this.notificationCommandPublisher.publishNotificationCommand(notificationCommandDto);
        } catch (Exception e) {
            log.warn("Error to send notification to user: " + userModel.getId());
        }
    }
}
