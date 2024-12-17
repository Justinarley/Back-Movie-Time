package com.example.back.users.controller;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Optional;
import java.util.Collections;
import com.example.back.users.model.User;
import org.springframework.http.ResponseEntity;
import com.example.back.users.dto.UserRequestDto;
import com.example.back.users.service.UserService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("api-users")
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
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody UserRequestDto userRequestDto) {
        String token = userService.authenticateUser(userRequestDto.getCorreo(), userRequestDto.getPassword());

        if (!token.equals("Contraseña incorrecta") && !token.equals("Usuario no encontrado")) {
            // Retornar el token y correo
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("correo", userRequestDto.getCorreo());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", token));
        }
    }

    // Obtener datos del perfil del usuario autenticado
    // @GetMapping("/profile")
    // public ResponseEntity<User> getProfile(@RequestHeader("Authorization") String
    // token) {
    // try {
    // // Decodificar el token y obtener el correo
    // String correo = userService.decodeToken(token);
    // Optional<User> user = userService.findByCorreo(correo);
    // if (user.isPresent()) {
    // return ResponseEntity.ok(user.get()); // Devuelve el objeto completo del
    // usuario
    // } else {
    // return ResponseEntity.status(404).body(null);
    // }
    // } catch (Exception e) {
    // return ResponseEntity.status(401).body(null);
    // }
    // }

    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(@RequestHeader("Authorization") String token) {
        try {
            // Verificar si el token tiene el prefijo "Bearer "
            if (token.startsWith("Bearer ")) {
                // Limpiar el prefijo "Bearer "
                String cleanToken = token.replace("Bearer ", "");
                // Decodificar el token y obtener el correo
                String correo = userService.decodeToken(cleanToken);
                Optional<User> user = userService.findByCorreo(correo);
                if (user.isPresent()) {
                    return ResponseEntity.ok(user.get());
                } else {
                    return ResponseEntity.status(404).body(null);
                }
            } else {
                // Si el token no tiene el prefijo "Bearer ", devolver 401
                return ResponseEntity.status(401).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body(null);
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<String> updateUserProfile(
            @RequestHeader("Authorization") String token,
            @RequestBody UserRequestDto userRequestDto) {
        try {
            String correo = userService.decodeToken(token.replace("Bearer ", ""));
            String result = userService.updateUserProfile(correo, userRequestDto);
            if ("Perfil actualizado correctamente".equals(result)) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result);
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token inválido o expirado");
        }
    }
}
