package food.delivery.dto;


import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeRole {

    private Integer employeeId;
    private List<String> roles;
}

