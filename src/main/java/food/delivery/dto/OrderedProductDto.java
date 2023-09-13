package food.delivery.dto;

import food.delivery.helper.AppMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Schema(name = "OrderedProductDto", description = " Buyurtma qilingan mahsulotlar ma'lumotlari classi")
public class OrderedProductDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long orderId;


    @Schema(description = "Product 'id'si shu bo'yicha mahsulot ma'lumotlari olinadi",
            accessMode = Schema.AccessMode.READ_WRITE)
    @Min(value = 1, message = AppMessages.NEGATIVE_VALUE)
    @NotNull(message = AppMessages.EMPTY_FIELD)
    private Integer productId;


    @Schema(description = "Mahsulot narxi",
            accessMode = Schema.AccessMode.READ_WRITE)
    @DecimalMin(value = "0.0", message = AppMessages.NEGATIVE_VALUE)
    @NotNull(message = AppMessages.EMPTY_FIELD)
    private Double price;


    @Schema(description = "Mahsulot chegirmasi. 0% dan 100% gacha bo'lishi mumkin",
            accessMode = Schema.AccessMode.READ_WRITE)
    @DecimalMin(value = "0.0", message = "discount min 0.0, max 100.0")
    @DecimalMax(value = "100.0", message = "discount min 0.0, max 100.0")
    @NotNull(message = AppMessages.EMPTY_FIELD)
    private Double discount;


    @Schema(description = "Mahsulotda chegirma bo'lsa, chegirma qancha bo'lishi saqlanadi",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Double discountPrice;


    @Schema(description = "Umumiy narx",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Double totalPrice;


    @Schema(description = "Mahsulot buyurtmasi miqdori",
            accessMode = Schema.AccessMode.READ_WRITE)
    @NotNull(message = AppMessages.EMPTY_FIELD)
    @Min(value = 1, message = "min value 1")
    private Integer amount;


//    @Schema(description = "Buyurtma 'id'si. Order classidan olinadi",
//            accessMode = Schema.AccessMode.READ_ONLY)
//    private Long orderId;

}