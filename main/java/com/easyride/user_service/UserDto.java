package com.easyride.user_service;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private String role;
    private Boolean enabled;
    private String verificationStatus;

    // 司机特有字段
    private String driverLicenseNumber;
    private String vehicleInfo;
}

