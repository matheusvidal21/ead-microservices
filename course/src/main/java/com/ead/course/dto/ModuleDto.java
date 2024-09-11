package com.ead.course.dto;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ModuleDto {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

}
