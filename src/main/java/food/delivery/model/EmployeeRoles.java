package food.delivery.model;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "employee_roles")
public class EmployeeRoles {

    @Id
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "employee_id")
    private Integer employeeId;

}
