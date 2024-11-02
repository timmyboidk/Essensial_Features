package com.easyride.user_service;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegistrationDto registrationDto) {
        userService.registerUser(registrationDto);
        return ResponseEntity.ok("注册成功");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = userService.authenticate(loginRequest);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getProfile(Authentication authentication) {
        String username = authentication.getName();
        UserDto userDto = userService.getUserProfile(username);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody UserDto userDto, Authentication authentication) {
        String username = authentication.getName();
        userService.updateUserProfile(username, userDto);
        return ResponseEntity.ok("资料更新成功");
    }
}
