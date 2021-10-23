package com.example.lesson7task1.service;

import com.example.lesson7task1.entity.User;
import com.example.lesson7task1.exeptions.ResourceNotFoundExсeption;
import com.example.lesson7task1.payload.ApiResponse;
import com.example.lesson7task1.payload.LoginDto;
import com.example.lesson7task1.payload.RegisterDto;
import com.example.lesson7task1.repository.RoleRepository;
import com.example.lesson7task1.repository.UserRepository;
import com.example.lesson7task1.security.JwtProvider;
import com.example.lesson7task1.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    RoleRepository rolerepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;

    public ApiResponse register(RegisterDto registerDto) {
        if (!registerDto.getPassword().equals(registerDto.getPrePassword())) {
            return new ApiResponse("Qayta yozilgan parolda xatolik", false);
        }
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return new ApiResponse("Bunday username royxatda bor", false);
        }

        User user = new User(registerDto.getFullname(), registerDto.getUsername(),
                passwordEncoder.encode(registerDto.getPassword()), rolerepository.findByName(Constants.USER).orElseThrow(() -> new ResourceNotFoundExсeption("ROLE ", "name", Constants.USER)),
                true);
        userRepository.save(user);
        return new ApiResponse("saved", true);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public ApiResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        User user = (User) authentication.getPrincipal();
        String token = jwtProvider.generateToken(user.getUsername(), user.getRole());
        return new ApiResponse("Ok", true, token);
    }
}
