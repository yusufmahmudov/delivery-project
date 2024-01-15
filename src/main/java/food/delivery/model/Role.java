package food.delivery.model;

import javax.persistence.*;
import javax.persistence.Table;

import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "roles")
public class Role {

    @Id
    private Integer id;

    @Column(length = 20, nullable = false, unique = true)
    private String name;

}
