package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.payload.AuthResponse;
import com.example.demo.payload.LoginRequest;
import com.example.demo.payload.SignupRequest;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        User user = authService.registerUser(signupRequest);

        return ResponseEntity.ok(new AuthResponse(
                user.getUsername(),
                user.getEmail(),
                "User Registered"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authService.authenticateUser(loginRequest);
        User user = (User) authentication.getPrincipal();
        String token = jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new AuthResponse(
                user.getUsername(),
                user.getEmail(),
                "User Logged in",
                token));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
        }
        return ResponseEntity.ok(new AuthResponse(null, null, "Logged out successfully!"));
    }

    @GetMapping("/current-user")
    public ResponseEntity<?> getCurrentUser() {
        User currentUser = authService.getCurrentUser();

        return ResponseEntity.ok(currentUser);
    }
}
