package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.ead.authuser.specifications.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserModel>> getAll(SpecificationTemplate.UserSpec spec,
                                                  @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                  @RequestParam(required = false) UUID courseId) {
        log.debug("GET getAllUsers spec {}, pageable {}", spec, pageable);
        Page<UserModel> userModelPage = null;
        if (courseId != null) {
            userModelPage = this.userService.findAll(SpecificationTemplate.userCourseId(courseId).and(spec), pageable);
        } else {
            userModelPage = this.userService.findAll(spec, pageable);
        }

                this.userService.findAll(spec, pageable);
        if (!userModelPage.isEmpty()) {
            for (UserModel user : userModelPage.toList()) {
                user.add(linkTo(methodOn(UserController.class).getOneUser(user.getId())).withSelfRel());
            }
        }

        return ResponseEntity.ok(userModelPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "id") UUID id) {
        log.debug("GET getOneUser userId {}", id);
        Optional<UserModel> userModelOptional = this.userService.findById(id);

        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        log.debug("GET getOneUser userId {}", userModelOptional.get().getId());
        return ResponseEntity.ok(userModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") UUID id) {
        log.debug("DELETE deleteUser userId {}", id);
        Optional<UserModel> userModelOptional = this.userService.findById(id);
        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        this.userService.delete(id);
        log.debug("DELETE deleteUser userId {}", id);
        log.info("User deleted successfully userId {}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") UUID id,
                                             @RequestBody @Validated(UserDto.UserView.UserPut.class) @JsonView(UserDto.UserView.UserPut.class) UserDto userDto) {
        log.debug("PUT updateUser userDto received userId {}", userDto.getId());

        Optional<UserModel> userModelOptional = this.userService.findById(id);
        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        var userModel = userModelOptional.get();
        userModel.setFullName(userDto.getFullName());
        userModel.setPhoneNumber(userDto.getPhoneNumber());
        userModel.setCpf(userDto.getCpf());
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        this.userService.save(userModel);
        log.debug("PUT updateUser userModel updated userId {}", userModel.getId());
        log.info("User updated successfully userId {}", userModel.getId());
        return ResponseEntity.ok(userModel);
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable(value = "userId") UUID userId,
                                             @RequestBody @Validated(UserDto.UserView.PasswordPut.class) @JsonView(UserDto.UserView.PasswordPut.class) UserDto userDto) {
        log.debug("PUT updatePassword userDto received userId {}", userDto.getId());
        Optional<UserModel> userModelOptional = this.userService.findById(userId);
        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (!userModelOptional.get().getPassword().equals(userDto.getOldPassword())){
            log.warn("Mismatched old password userId {}", userId);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Mismatched old password");
        }

        var userModel = userModelOptional.get();
        userModel.setPassword(userDto.getPassword());
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        this.userService.save(userModel);
        log.debug("PUT updatePassword userModel updated userId {}", userModel.getId());
        log.info("Password updated successfully userId {}", userModel.getId());
        return ResponseEntity.ok("Password updated successfully");
    }

    @PutMapping("/{userId}/image")
    public ResponseEntity<Object> updateImage(@PathVariable(value = "id") UUID userId,
                                                 @RequestBody @Validated(UserDto.UserView.ImagePut.class) @JsonView(UserDto.UserView.ImagePut.class) UserDto userDto) {
        log.debug("PUT updateImage userDto received userId {}", userDto.getId());
        Optional<UserModel> userModelOptional = this.userService.findById(userId);
        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        var userModel = userModelOptional.get();
        userModel.setImageUrl(userDto.getImageUrl());
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        this.userService.save(userModel);
        log.debug("PUT updateImage userModel updated userId {}", userModel.getId());
        return ResponseEntity.ok(userModel);
    }

}
