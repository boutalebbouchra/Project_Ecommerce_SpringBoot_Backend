package com.example.projet_ecommerce.services;
import com.example.projet_ecommerce.entities.User;
import com.example.projet_ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public User addUser(User user);
    public User updateUser(Long uuidUser, User updatedUser);
    public void deleteUser(Long uuidUser);
    public Optional<User> getUserById(Long uuIdUser);
    public List<User> getAllUsers();
    public void updateUserPassword(Long uuIdUser, String newPassword);
}
