package pdp.uz.apprealauthentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pdp.uz.apprealauthentication.payload.ApiResponse;
import pdp.uz.apprealauthentication.payload.LoginDto;
import pdp.uz.apprealauthentication.payload.RegisterDto;
import pdp.uz.apprealauthentication.service.AuthService;

import javax.persistence.SqlResultSetMapping;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Lazy
    @Autowired
    AuthService authService;

    /**
     * For Register User and send verification code
     * @param registerDto
     * @return class ApiResponse{String message,boolean success}
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto registerDto){
        ApiResponse apiResponse = authService.registerUser(registerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
    }


    /**
     * Send email tto user verify code
     *
     * @param emailCode
     * @param email
     * @return class ApiResponse{String message ,boolean success}
     */

    @GetMapping("/verifyEmail")
    public ResponseEntity<?> enableUser(@RequestParam String emailCode, @RequestParam String email){
        ApiResponse apiResponse = authService.verifyEmail(email, emailCode);
        return ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
    }


    /**
     * Login user with email and password
     *
     * @param loginDto
     * @return  class ApiResponse{String message ,boolean success}
     */
    @PostMapping("/login")
    public ResponseEntity<?> registerUser(@RequestBody LoginDto loginDto){
        ApiResponse apiResponse = authService.login(loginDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
    }



}
