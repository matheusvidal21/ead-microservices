package com.ead.course.services.impl;

import com.ead.course.models.CourseModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.CourseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
}