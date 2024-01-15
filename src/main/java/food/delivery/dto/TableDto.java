package food.delivery.dto;


import food.delivery.helper.AppMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Schema(name = "TableDto", description = "Filiallarni stollarini ma'lumotlarini saqlovchi class")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TableDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;


    @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true,
            description = "Filialdagi stol raqami")
    @NotNull(message = AppMessages.EMPTY_FIELD)
    private Integer tableNo;


    @Schema(accessMode = Schema.AccessMode.READ_WRITE,
            description = "Stol band yoki band emasligi")
    private Boolean busy;


    @Schema(accessMode = Schema.AccessMode.READ_WRITE, required = true)
    private Integer filialId;

}
