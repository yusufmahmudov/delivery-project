package food.delivery.model;

import lombok.*;

import javax.persistence.*;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name", length = 130)
    private String fullName;

    @Column(name = "username", length = 64)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birth_date")
    private LocalDateTime birthDate;

    @Column(name = "salt", length = 20)
    private String salt;

    @Column(name = "tool_word", length = 20)
    private String toolWord;

    @Column(name = "phone_num1", length = 20)
    private String phoneNum1;

    @Column(name = "phone_num2", length = 20)
    private String phoneNum2;

    @Column(name = "tg_username")
    private String tgUsername;

    @Column(name = "tg_id")
    private Long tgId;

    @Column(name = "language_code", length = 20)
    private String languageCode;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "active")
    private Boolean isActive;

    @Column(name = "admin")
    private Boolean isAdmin;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

}