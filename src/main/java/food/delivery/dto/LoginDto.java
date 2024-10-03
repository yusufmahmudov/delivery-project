package food.delivery.dto;


import food.delivery.helper.AppMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "LoginDto", description = "Sign In")
public class LoginDto {

    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY, required = true,
            description = "Employee uchun username, User uchun phoneNum")
    @NotNull(message = AppMessages.EMPTY_FIELD)
    private String username;


    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY, required = true,
            description = "Parol")
    @NotNull(message = AppMessages.EMPTY_FIELD)
    private String password;

}
