package food.delivery.dto;


import food.delivery.helper.AppMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Schema(name = "LocationDto", description = "User Location ma'lumotlarini saqlovchi class")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LocationDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;


    @Schema(description = "Foydalanuvchi manzili",
            accessMode = Schema.AccessMode.READ_WRITE)
    @Size(max = 255, message = "length max 255")
    private String address;


    @Schema(description = "Foydalanuvchini manzilini saqlovchi latitude",
            accessMode = Schema.AccessMode.READ_WRITE)
    @NotNull(message = AppMessages.EMPTY_FIELD)
    private Double latitude;


    @Schema(description = "Foydalanuvchini manzilini saqlovchi longitude",
            accessMode = Schema.AccessMode.READ_WRITE)
    @NotNull(message = AppMessages.EMPTY_FIELD)
    private Double longitude;


    @Schema(accessMode = Schema.AccessMode.READ_WRITE)
//    @NotNull(message = AppMessages.EMPTY_FIELD)
    private Long userId;

}
