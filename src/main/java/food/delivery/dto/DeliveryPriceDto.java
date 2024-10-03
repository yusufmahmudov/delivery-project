package food.delivery.dto;

import food.delivery.helper.AppMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Schema(name = "DeliveryPriceDto", description = "Yetqazib berish narxlarini o'zida saqlovchi class")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DeliveryPriceDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "Odatiy-o'zgarmas narx",
            accessMode = Schema.AccessMode.READ_WRITE, required = true)
    @NotNull(message = AppMessages.EMPTY_FIELD)
    @DecimalMin(value = "0.0", message = AppMessages.NEGATIVE_VALUE)
    private Double price;

    @Schema(description = "Belgilangan hududdan uzoq joylar uchun alohida narx")
    @DecimalMin(value = "0.0", message = AppMessages.NEGATIVE_VALUE)
    private Double priceOut;

    @Schema(description = "KM bilan hisoblash uchun narx")
    @DecimalMin(value = "0.0", message = AppMessages.NEGATIVE_VALUE)
    private Double distancePrice;

    // TODO: ???
    private Boolean active;

}