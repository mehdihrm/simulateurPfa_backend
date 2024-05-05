package com.simbancaire.simulateurpfa.repositories;
import com.simbancaire.simulateurpfa.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUserName(String userName);

    boolean existsByEmail(String email);

    boolean existsByUserName(String username);
}
