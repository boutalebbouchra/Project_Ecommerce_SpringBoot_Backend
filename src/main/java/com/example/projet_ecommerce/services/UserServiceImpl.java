package com.example.projet_ecommerce.services;
import com.example.projet_ecommerce.entities.User;
import com.example.projet_ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public User addUser(User user) {
        // Vérifier si l'email est unique
        if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
            throw new IllegalArgumentException("L'email est déjà utilisé par un autre utilisateur.");
        }
        // Vérifications personnalisées
        validateUserFields(user);
        // Enregistrer l'utilisateur s'il passe toutes les validations
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long uuidUser, User updatedUser) {
        Optional<User> existingUserOptional = userRepository.findById(uuidUser);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            // Mettez à jour les champs que vous souhaitez autoriser à être modifiés
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setSex(updatedUser.getSex());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhoto(updatedUser.getPhoto());
            existingUser.setDateInscription(updatedUser.getDateInscription());

            // Enregistrez la mise à jour
            return userRepository.save(existingUser);
        } else {
            throw new IllegalArgumentException("Utilisateur non trouvé avec l'ID : " + uuidUser);
        }
    }
    @Override
    public void deleteUser(Long uuidUser) {
        Optional<User> existingUserOptional = userRepository.findById(uuidUser);

        if (existingUserOptional.isPresent()) {
            userRepository.deleteById(uuidUser);
        } else {
            throw new IllegalArgumentException("Utilisateur non trouvé avec l'ID : " + uuidUser);
        }
    }

    @Override
    public Optional<User> getUserById(Long uuIdUser) {
        return userRepository.findById(uuIdUser);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUserPassword(Long uuIdUser, String newPassword) {
        Optional<User> existingUserOptional = userRepository.findById(uuIdUser);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            // Vérifiez la force du mot de passe
            String passwordStrength = checkPasswordStrength(newPassword);

            // Mettez à jour le mot de passe si la force est suffisante
            if ("fort".equals(passwordStrength)) {
                existingUser.setPassword(existingUser.getPassword());
                userRepository.save(existingUser);
            } else {
                throw new IllegalArgumentException("Le mot de passe n'est pas assez fort : " + passwordStrength);
            }
        } else {
            throw new IllegalArgumentException("Utilisateur non trouvé avec l'ID : " + uuIdUser);
        }
    }

    private void validateUserFields(User user) {
        // Vérification de la date d'inscription
        if ((user.getDateInscription() == null || user.getDateInscription().isAfter(LocalDate.now())) && user.getLabel() == "client") {
            throw new IllegalArgumentException("La date d'inscription doit être présente et dans le passé.");
        }
    }

    private String checkPasswordStrength(String password) {
        if (password.length() >= 8 && containsDigitsAndLetters(password) && containsSpecialCharacters(password)) {
            return "fort";
        } else if (password.length() >= 8 && containsDigitsAndLetters(password)) {
            return "passable";
        } else {
            return "faible";
        }
    }
    private boolean containsDigitsAndLetters(String password) {
        return password.matches(".*\\d.*") && password.matches(".*[a-zA-Z].*");
    }

    private boolean containsSpecialCharacters(String password) {
        // Ajoutez ici vos caractères spéciaux, par exemple : @,_#
        return password.matches(".*[@_#].*");
    }

}
