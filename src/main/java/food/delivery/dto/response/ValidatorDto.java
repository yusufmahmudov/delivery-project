package food.delivery.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "ValidatorDto", description = "Xatoliklar qaytarish uchun yordamchi class")
public class ValidatorDto {

    private String fieldName;
    private String error;
}
