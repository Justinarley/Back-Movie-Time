package com.example.back.users.service;

import java.util.List;
import java.util.Optional;
import com.example.back.users.model.User;
import org.springframework.stereotype.Service;
import com.example.back.users.dto.UserRequestDto;
import com.example.back.users.security.TokenUtil;
import com.example.back.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Obtener todos los usuarios
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public String registerUser(UserRequestDto userRequestDto) {
        // Verificar si ya existe un usuario con la misma cédula
        if (userRepository.findByCedula(userRequestDto.getCedula()).isPresent()) {
            return "La cédula ya está registrada.";
        }

        // Verificar si ya existe un usuario con el mismo correo
        if (userRepository.findByCorreo(userRequestDto.getCorreo()).isPresent()) {
            return "El correo ya está registrado.";
        }

        // Crear un nuevo usuario y mapear los datos del DTO
        User newUser = new User();
        newUser.setCedula(userRequestDto.getCedula());
        newUser.setNombre(userRequestDto.getNombre());
        newUser.setApellido(userRequestDto.getApellido());
        newUser.setCorreo(userRequestDto.getCorreo());
        newUser.setPassword(userRequestDto.getPassword());
        newUser.setCelular(userRequestDto.getCelular());
        newUser.setFechaNacimiento(userRequestDto.getFechaNacimiento());

        // Guardar el usuario en la base de datos
        userRepository.save(newUser);

        return "Usuario registrado exitosamente.";
    }

    public String authenticateUser(String correo, String password) {
        Optional<User> existingUser = userRepository.findByCorreo(correo);
    
        if (existingUser.isPresent()) {
            User user = existingUser.get();
    
            if (password.equals(user.getPassword())) {
                // Generar el token
                String token = TokenUtil.generateToken(user.getCorreo());
                return token;
            } else {
                return "Contraseña incorrecta";
            }
        } else {
            return "Usuario no encontrado";
        }
    }

    // Decodificar el token para obtener el correo del usuario
    public String decodeToken(String token) {
        try {
            // Decodifica el token y obtiene el correo
            String correo = TokenUtil.decodeToken(token);
            return correo;
        } catch (Exception e) {
            throw new RuntimeException("Token inválido o expirado", e);
        }
    }

    // Buscar un usuario por su correo
    public Optional<User> findByCorreo(String correo) {
        return userRepository.findByCorreo(correo);
    }

    // Actualizar el perfil del usuario autenticado
    public String updateUserProfile(String correo, UserRequestDto userRequestDto) {
        Optional<User> existingUser = userRepository.findByCorreo(correo);

        if (existingUser.isPresent()) {
            User user = existingUser.get();

            // Actualizar los datos del usuario
            user.setNombre(userRequestDto.getNombre());
            user.setApellido(userRequestDto.getApellido());
            user.setCelular(userRequestDto.getCelular());
            user.setFechaNacimiento(userRequestDto.getFechaNacimiento());

            // Validar y actualizar correo (opcional)
            if (!user.getCorreo().equals(userRequestDto.getCorreo())) {
                if (userRepository.findByCorreo(userRequestDto.getCorreo()).isPresent()) {
                    return "El nuevo correo ya está en uso.";
                }
                user.setCorreo(userRequestDto.getCorreo());
            }

            // Guardar los cambios
            userRepository.save(user);
            return "Perfil actualizado correctamente";
        } else {
            return "Usuario no encontrado";
        }
    }
}
