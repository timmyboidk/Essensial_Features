package com.easyride.user_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public void registerUser(UserRegistrationDto registrationDto) {
        if (userRepository.existsByUsername(registrationDto.getUsername())) {
            throw new UserAlreadyExistsException("用户名已存在");
        }

        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setEmail(registrationDto.getEmail());
        user.setRole(registrationDto.getRole());
        user.setEnabled(true);

        if ("DRIVER".equalsIgnoreCase(registrationDto.getRole())) {
            user.setDriverLicenseNumber(registrationDto.getDriverLicenseNumber());
            user.setVehicleInfo(registrationDto.getVehicleInfo());
            user.setVerificationStatus("PENDING");
        }

        userRepository.save(user);
    }

    @Override
    public String authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public UserDto getUserProfile(String username) {
        User user = getUserByUsername(username);
        return mapToUserDto(user);
    }

    @Override
    public void updateUserProfile(String username, UserDto userDto) {
        User user = getUserByUsername(username);

        // 更新可修改的字段
        user.setEmail(userDto.getEmail());

        if ("DRIVER".equalsIgnoreCase(user.getRole())) {
            user.setVehicleInfo(userDto.getVehicleInfo());
            // 若涉及需要重新审核的字段，更新 verificationStatus 为 "PENDING"
        }

        userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        userDto.setEnabled(user.getEnabled());
        userDto.setVerificationStatus(user.getVerificationStatus());
        userDto.setDriverLicenseNumber(user.getDriverLicenseNumber());
        userDto.setVehicleInfo(user.getVehicleInfo());
        return userDto;
    }
}
