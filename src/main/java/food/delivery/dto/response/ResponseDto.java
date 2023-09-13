package food.delivery.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Schema(name = "ResponseDto",
        description = "Responseda qaytuvchi ma'lumotlar. " +
                "boolean -> success, code -> Integer, " +
                "message -> String, data -> body, errors -> Validator")
public class ResponseDto<T> {

    private boolean success;

    private Integer code;

    private String message;

    private T data;

    private List<ValidatorDto> errors;

}
