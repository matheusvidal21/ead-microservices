package com.ead.course.services.impl;

import com.ead.course.clients.AuthUserClient;
import com.ead.course.models.CourseModel;
import com.ead.course.models.CourseUserModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.CourseUserRepository;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.CourseService;
import com.ead.course.specifications.SpecificationTemplate;
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
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseUserRepository courseUserRepository;

    @Autowired
    private AuthUserClient authUserClient;

    @Transactional
    @Override
    public void delete(CourseModel course) {
        boolean deleteCourseInAuthUser = false;
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
        List<CourseUserModel> courseUserModelList = this.courseUserRepository.findAllCourseUserIntoCourse(course.getId());
        if (!courseUserModelList.isEmpty()){
            this.courseUserRepository.deleteAll(courseUserModelList);
            deleteCourseInAuthUser = true;
        }

        this.courseRepository.delete(course);
        if (deleteCourseInAuthUser) {
            this.authUserClient.deleteCourseInAuthUser(course.getId());
        }
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
}
