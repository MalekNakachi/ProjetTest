package com.example.gestionAchat.repository.pm;

import com.example.gestionAchat.entities.PasswordResetToken;
import com.example.gestionAchat.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUseriD(User user);
}
