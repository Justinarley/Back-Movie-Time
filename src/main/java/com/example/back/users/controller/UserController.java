package com.example.back.users.controller;

import java.util.List;
import com.example.back.users.model.User;
import org.springframework.http.ResponseEntity;
import com.example.back.users.dto.UserRequestDto;
import com.example.back.users.service.UserService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api-users")
@CrossOrigin(origins = "http://localhost:8081")
public class UserController {
    @Autowired
    private UserService userService;

    // Obtener todos los usuarios
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequestDto userRequestDto) {
        String result = userService.registerUser(userRequestDto);
        if (result.contains("exitosamente")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserRequestDto userRequestDto) {
        String result = userService.authenticateUser(userRequestDto.getCorreo(), userRequestDto.getPassword());
        if (result.equals("Autenticación exitosa")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
    
}
