package com.easyride.user_service;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationDto {

    @NotBlank
    @Size(max = 50)
    private String username;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    @Size(max = 100)
    @Email
    private String email;

    @NotBlank
    private String role; // "PASSENGER" 或 "DRIVER"

    // 司机特有字段
    private String driverLicenseNumber;
    private String vehicleInfo;
}

