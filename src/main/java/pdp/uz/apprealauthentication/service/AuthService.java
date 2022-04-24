package pdp.uz.apprealauthentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pdp.uz.apprealauthentication.entity.Role;
import pdp.uz.apprealauthentication.entity.User;
import pdp.uz.apprealauthentication.entity.enums.RoleEnum;
import pdp.uz.apprealauthentication.payload.ApiResponse;
import pdp.uz.apprealauthentication.payload.LoginDto;
import pdp.uz.apprealauthentication.payload.RegisterDto;
import pdp.uz.apprealauthentication.repository.RoleRepo;
import pdp.uz.apprealauthentication.repository.UserRepo;
import pdp.uz.apprealauthentication.security.JwtProvider;

import java.util.*;


@Service
public class AuthService implements UserDetailsService {


    @Autowired
    UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    JavaMailSender javaMailSender;

    /**
     * Register User to server
     *
     * @param registerDto
     * @return class ApiResponse { String message,boolean success}
     */
    public ApiResponse registerUser(RegisterDto registerDto) {
        boolean existByEmail = userRepo.existsByEmail(registerDto.getEmail());
        //Check email to be unique
        if (existByEmail)
            return new ApiResponse("Email already exist!", false);

        boolean existsByPassword = userRepo.existsByPassword(registerDto.getPassword());
        //Check password to be unique
        if (existsByPassword)
            return new ApiResponse("Password already exist!", false);

        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRoles(Collections.singleton(roleRepo.findByRoleEnum(RoleEnum.ROLE_USER)));
        user.setEmailCode(UUID.randomUUID().toString());
        userRepo.save(user);
        //todo : send message to email and make user enable
        sendEmail(registerDto.getEmail(), user.getEmailCode());

        return new ApiResponse("Successfully registered,So and Confirm your email from your inbox", true);

    }


    /**
     * Send email to user verify code
     *
     * @param emailCode
     * @param email
     * @return class ApiResponse{String message ,boolean success}
     */
    public ApiResponse verifyEmail(String email, String emailCode) {

        Optional<User> optionalUser = userRepo.findByEmailAndEmailCode(email, emailCode);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();


            user.setEnabled(true);
            user.setEmailCode(null);

            userRepo.save(user);
            return new ApiResponse("Successfully verified", true);
        }
        return new ApiResponse("Wrong email or verification code", false);
    }


    /**
     * Login user with email and password
     *
     * @param loginDto
     * @return  class ApiResponse{String message ,boolean success}
     */
    public ApiResponse login(LoginDto loginDto) {

        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword()));
            User user = (User) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(loginDto.getUsername(), user.getRoles());
            return new ApiResponse("Token",true,token);
        } catch (BadCredentialsException badCredentialsException) {
             return new ApiResponse("Username and password not match!",false);
        }
    }


    public Boolean sendEmail(String sendingEmail, String emailCode) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("Mr.Javakhir");
            message.setTo(sendingEmail);
            message.setSubject("Confirm your email");
            message.setText("<a href='http://localhost:8080/api/auth/verifyEmail?emailCode=" + emailCode + "&email=" + sendingEmail + "'>Confirm</a>");
            javaMailSender.send(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

// →→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→  Overrides →→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→♥


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> optionalUser = userRepo.findByUsername(username);
//        if(optionalUser.isPresent())
//            return optionalUser.get();
//        throw new UsernameNotFoundException(username+" not found");
        return userRepo.findByEmail(username).orElseThrow(() -> new RuntimeException(username + " not found"));
    }

}
