package com.example.projet_ecommerce.repositories;

import com.example.projet_ecommerce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {



    List<User> findByIdUser(String lastName);
    List<User> findByUuidUser(String lastName);
    List<User> findByLastName(String lastName);
    List<User> findByFirstName(String lastName);
    List<User> findByEmail(String lastName);


}
