package com.ead.course.controllers;

import com.ead.course.dto.ModuleDto;
import com.ead.course.models.CourseModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.services.CourseService;
import com.ead.course.services.ModuleService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/courses/{courseId}/modules")
    public ResponseEntity<Object> save(@RequestBody @Valid ModuleDto module, @PathVariable UUID courseId) {
        Optional<CourseModel> courseModelOptional = this.courseService.findById(courseId);
        if (courseModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
        var moduleModel = new ModuleModel();
        BeanUtils.copyProperties(module, moduleModel);
        moduleModel.setCourse(courseModelOptional.get());
        moduleModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        moduleModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(this.moduleService.save(moduleModel));
    }

    @DeleteMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> delete(@PathVariable UUID courseId, @PathVariable UUID moduleId) {
        Optional<ModuleModel> moduleModelOptional = this.moduleService.findModuleIntoCourse(courseId, moduleId);
        if (moduleModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course");
        }
        this.moduleService.delete(moduleModelOptional.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> update(@PathVariable UUID courseId, @PathVariable UUID moduleId, @RequestBody @Valid ModuleDto module) {
        Optional<ModuleModel> moduleModelOptional = this.moduleService.findModuleIntoCourse(courseId, moduleId);
        if (moduleModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course");
        }
        var moduleModel = moduleModelOptional.get();
        moduleModel.setTitle(module.getTitle());
        moduleModel.setDescription(module.getDescription());
        moduleModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.OK).body(this.moduleService.save(moduleModel));
    }

    @GetMapping("/courses/{courseId}/modules")
    public ResponseEntity<List<ModuleModel>> getAllByCourse(@PathVariable UUID courseId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.moduleService.findAllByCourse(courseId));
    }

    @GetMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> getOneByCourse(@PathVariable UUID courseId, @PathVariable UUID moduleId) {
        Optional<ModuleModel> moduleModelOptional = this.moduleService.findModuleIntoCourse(courseId, moduleId);
        if (moduleModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course");
        }
        return ResponseEntity.status(HttpStatus.OK).body(moduleModelOptional.get());
    }


}
