package com.ead.course.dto;

import com.ead.course.enums.UserStatus;
import com.ead.course.enums.UserType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private UUID id;

    private String username;

    private String email;

    private String fullName;

    private UserStatus userStatus;

    private UserType userType;

    private String phoneNumber;

    private String cpf;

    private String imageUrl;
}
