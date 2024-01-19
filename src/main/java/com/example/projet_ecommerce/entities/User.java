package com.example.projet_ecommerce.entities;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Data
@Table(name = "User")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUser;

    @Column(columnDefinition = "varchar(36)")
    private String uuidUser;

    @NotBlank(message = "le label n est pas trouvé")
    @Column(nullable = false,length = 20)
    private String Label;

    @NotBlank(message = "le nom n est pas trouvé")
    @Column(nullable = false,length = 20)
    private String LastName;

    @NotBlank(message = "le prenom n est pas trouvé")
    @Column(nullable = false,length = 20)
    private String FirstName;

    @NotBlank(message = "le sex n est pas trouvé")
    @Column(nullable = false)
    private String sex;

    @Email(message = "Email doit etre valide")
    @NotBlank(message = "l'email n est pas trouvé")
    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false,length = 30)
    private String password;

    @Column(nullable = false)
    private boolean statue;

    private String photo;
    @Column(nullable = false)
    private LocalDate dateInscription;

    public User() {
        this.uuidUser = UUID.randomUUID().toString();
        this.dateInscription = LocalDate.now();
    }


}
