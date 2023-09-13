package food.delivery.dto;

import food.delivery.helper.AppMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(name = "Product", description = "Mahsulot ma'lumotlarini saqlovchi class")
public class ProductDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;


    @Schema(description = "Mahsulot nomi. unique bo'ladi",
            accessMode = Schema.AccessMode.READ_WRITE, required = true)
    @NotNull(message = AppMessages.EMPTY_FIELD)
    @Size(min = 3, max = 120, message = "length min 3 and max 120")
    private String name;


    @Schema(description = "Mahsulot narxi",
            accessMode = Schema.AccessMode.READ_WRITE, required = true)
    @DecimalMin(value = "0.0", message = AppMessages.NEGATIVE_VALUE)
    private Double price;


    @Schema(description = "Chegirma foizi. 0% dan 100% gacha qabul qilinadi", defaultValue = "0.0",
            accessMode = Schema.AccessMode.READ_WRITE)
    @DecimalMin(value = "0.0", message = "min value = 0.0")
    @DecimalMax(value = "100.0", message = "max value = 100.0")
    private Double discount;


    @Schema(description = "Mahsulot sotuvda mavjud bo'lsa true, aks holda false", defaultValue = "false",
            accessMode = Schema.AccessMode.READ_WRITE)
    private Boolean active;


    @Schema(description = "Mahsulot rasmi saqlanuvchi fayl path",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String imagePath;


    @Schema(description = "Mahsulot rasmi saqlovchi fayl id",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long imageId;


    @Schema(description = "Mahsulot bilan qo'shib olish mumkin bo'lgan mahsulot",
            accessMode = Schema.AccessMode.READ_WRITE, defaultValue = "false")
    private Boolean extra;


    @Schema(description = "Mahsulot ta'rifi", defaultValue = "null",
            accessMode = Schema.AccessMode.READ_WRITE)
    private String description;


    @Schema(description = "Mahsulot bilan qo'shib olish mumkin bo'lgan mahsulotlar",
            accessMode = Schema.AccessMode.READ_WRITE)
    private List<Integer> extraList;


    @Schema(description = "Mahsulotga qo'shib beriladigan bonus",
            accessMode = Schema.AccessMode.READ_WRITE)
    private List<Integer> bonusList;


    @Schema(description = "Mahsulot qaysi kategoriyaga kirishini saqlaydi",
            accessMode = Schema.AccessMode.READ_WRITE, required = true)
    private CategoryDto categoryDto;

}