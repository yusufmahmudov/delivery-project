package food.delivery.model;


import javax.persistence.*;
import javax.persistence.Table;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "employee_id_seq")
    @SequenceGenerator(name = "employee_id_seq", sequenceName = "employee_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name", length = 30)
    private String firstName;

    @Column(name = "last_name", length = 30)
    private String lastName;

    @Column(nullable = false, unique = true, length = 64)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "salt", length = 20)
    private String salt;

    @Column(name = "phone_num1", length = 20)
    private String phoneNum1;

    @Column(name = "phone_num2", length = 20)
    private String phoneNum2;

//    @Column(name = "tool_word", length = 20)
//    private String toolWord;

    @Column(name = "address", length = 120)
    private String address;

    @Column(name = "code", length = 120)
    private String code;

    @Column(name = "gender")
    private String gender;

    @Column(name = "is_active")
    private Boolean active;

    @Column(name = "workplace", length = 20)
    private String workplace;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "passport_no", length = 20)
    private String passportNo;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "image_id")
    private Long imageId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "employee_roles",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

}
