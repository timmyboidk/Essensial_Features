package com.easyride.user_service;

public interface UserService {

    void registerUser(UserRegistrationDto registrationDto);

    String authenticate(LoginRequest loginRequest);

    UserDto getUserProfile(String username);

    void updateUserProfile(String username, UserDto userDto);

    User getUserByUsername(String username);
}

