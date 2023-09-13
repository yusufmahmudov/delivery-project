package food.delivery.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "JwtResponse", description = "Tokenni yuborish uchun response ma'lumotlar")
public class JwtResponse {

    @Schema(description = "Dasturga kirish uchun token")
    private String token;

    private String type = "Bearer";

    @Schema(description = "Foydalanuvchi idsi")
    private Long id;

    @Schema(description = "Foydalanuvchi nomi")
    private String username;

    @Schema(description = "Foydalanuvchi rolelari")
    private List<String> roles;

    public JwtResponse(Long id, String username, String token, List<String> roles) {
        this.id = id;
        this.username = username;
        this.token = token;
        this.roles = roles;
    }
}
