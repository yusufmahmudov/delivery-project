package food.delivery.dto;

import food.delivery.helper.AppMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.util.List;

@Schema(name = "CategoryDto", description = "Product kategoriyalarini o'zida saqlovchi class")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true,
        description = "Category name, unique bo'ladi")
    @NotNull(message = AppMessages.EMPTY_FIELD)
    @Size(min = 3, max = 50, message = "length min 3 and max 50 ")
    private String name;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<ProductDto> products;

}
