package food.delivery.dto.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "ImageDto", description = "Product rasmlarini saqlovchi class")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ImageDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Rasmning nomi", accessMode = Schema.AccessMode.READ_ONLY)
    private String name;

    @Schema(description = "Rasm saqlanayotgan fayl path", accessMode = Schema.AccessMode.READ_ONLY)
    private String imagePath;

    @Schema(description = "Rasmning turi", accessMode = Schema.AccessMode.READ_ONLY)
    private String type;

    @Schema(description = "Position", accessMode = Schema.AccessMode.READ_ONLY)
    private String position;

}