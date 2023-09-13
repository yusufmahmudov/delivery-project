package food.delivery.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import food.delivery.helper.AppMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;


@Schema(name = "FilialDto", description = "Filiallarni ma'lumotlarini saqlovchi class")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FilialDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;


    @Schema(description = "Filial nomi yoki filial joylashuvi nomi. unique bo'ladi", required = true)
    @Size(min = 4, max = 255, message = "length min 4 and max 255")
    @NotNull(message = AppMessages.EMPTY_FIELD)
    private String name;


    @Schema(description = "Location latitude", required = true)
    @NotNull(message = AppMessages.EMPTY_FIELD)
    private Double latitude;


    @Schema(description = "Location longitude", required = true)
    @NotNull(message = AppMessages.EMPTY_FIELD)
    private Double longitude;


    @Schema(description = "Filial manzili, joylashuvi", required = true)
    @Size(min = 4, max = 255, message = "length min 4 and max 255")
    @NotNull(message = AppMessages.EMPTY_FIELD)
    private String address;


    @Schema(description = "Filial hajmi haqida ma'lumot, kattaligi", defaultValue = "null")
    private String buildingSize;


    @Schema(description = "Filialning odam sig'imi", defaultValue = "0")
    @Min(value = 0)
    private Integer humanCapacity;


    @Schema(description = "Filialga biirktirilgan telefon raqam", required = true,
            example = "+998-90-999-99-99")
    @NotNull(message = AppMessages.EMPTY_FIELD)
    @Pattern(regexp = "^([+]?\\d{3}[-\\s]?|)\\d{2}[-\\s]?\\d{3}[-\\s]?\\d{2}[-\\s]?\\d{2}$",
            message = "Invalid phone number")
    private String phoneNum;


    @Schema(description = "Filial ochilish vaqti", required = true)
    @NotNull(message = AppMessages.EMPTY_FIELD)
    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private String openingTime;


    @Schema(description = "Filial yopilish vaqti", required = true)
    @NotNull(message = AppMessages.EMPTY_FIELD)
    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private String closingTime;


    @Schema(description = "Filialga biriktirilgan Admin 'id'si", required = true)
    @NotNull(message = AppMessages.EMPTY_FIELD)
    @Min(value = 1, message = AppMessages.NEGATIVE_VALUE)
    private Integer adminId;


    @Schema(description = "Xodimlarning idlari")
    private List<Integer> employees;


    @Schema(description = "Filial rasmi saqlanuvchi path",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String imagePath;


    @Schema(description = "Filial ishlayotgan bo'lsa true, aks holda false", defaultValue = "false")
    private Boolean active;

}