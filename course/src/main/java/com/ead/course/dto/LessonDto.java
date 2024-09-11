package com.ead.course.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class LessonDto {

    @NotBlank
    private String title;

    private String description;

    @NotBlank
    private String videoUrl;

}
