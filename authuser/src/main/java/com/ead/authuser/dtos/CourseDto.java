package com.ead.authuser.dtos;

import com.ead.authuser.enums.CourseLevel;
import com.ead.authuser.enums.CourseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDto {

    private UUID id;

    private String name;

    private String description;

    private String imageUrl;

    private CourseStatus courseStatus;

    private UUID userInstructor;

    private CourseLevel courseLevel;


}
