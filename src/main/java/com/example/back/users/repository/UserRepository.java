package com.example.back.users.repository;

import java.util.Optional;
import com.example.back.users.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByCedula(String cedula);
  Optional<User> findByCorreo(String correo);
}
