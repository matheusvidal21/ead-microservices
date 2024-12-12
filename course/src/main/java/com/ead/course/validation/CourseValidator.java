package com.ead.course.validation;

import com.ead.course.configs.security.AuthenticationCurrentUserService;
import com.ead.course.dto.CourseDto;
import com.ead.course.enums.UserType;
import com.ead.course.models.UserModel;
import com.ead.course.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.nio.file.AccessDeniedException;
import java.util.Optional;
import java.util.UUID;

@Component
public class CourseValidator implements Validator {

    @Autowired
    @Qualifier("defaultValidator")
    private Validator validator;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationCurrentUserService authenticationCurrentUserService;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        CourseDto courseDto = (CourseDto) target;
        validator.validate(courseDto, errors);
        if (!errors.hasErrors()) {
            try {
                validateUserInstructor(courseDto.getUserInstructor(), errors);
            } catch (AccessDeniedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void validateUserInstructor(UUID userInstructor, Errors errors) throws AccessDeniedException {
        UUID currentUserId = this.authenticationCurrentUserService.getCurrentUser().getUserId();
        if (currentUserId.equals(userInstructor)){
            Optional<UserModel> userModelOptional = this.userService.findById(userInstructor);
            if(userModelOptional.isEmpty()) {
                errors.rejectValue("userInstructor", "UserInstructorError", "Instructor not found");
            }
            if(userModelOptional.get().getUserType().equals(UserType.STUDENT.toString())){
                errors.rejectValue("userInstructor", "UserInstructorError", "User must be INSTRUCTOR or ADMIN");
            }
        } else {
            throw new AccessDeniedException("User not allowed to create course for another user");
        }
    }
}
