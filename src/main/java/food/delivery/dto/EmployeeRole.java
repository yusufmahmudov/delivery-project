package food.delivery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(name = "EmployeeRole", description = "Employee Role berish")
public class EmployeeRole {

    @Schema(description = "employeeId")
    private Integer employeeId;

    @Schema(description = "roles")
    private List<String> roles;

}

