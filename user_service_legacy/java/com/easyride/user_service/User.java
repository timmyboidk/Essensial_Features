package com.easyride.user_service;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(unique = true, nullable = false)
    private String username; // 手机号或用户名

    @NotBlank
    @Size(max = 100)
    private String password;

    @Size(max = 100)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String role; // "PASSENGER" 或 "DRIVER"

    @Column(nullable = false)
    private Boolean enabled = true;

    // 司机特有字段
    private String driverLicenseNumber;
    private String vehicleInfo;

    // 审核状态：PENDING, APPROVED, REJECTED
    private String verificationStatus;
}
