package food.delivery.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Schema(name = "ReportDto", description = "Statistika ma'lumotlarini saqlovchi class")
public class ReportDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY,
            description = "Qaytarilayotgan ma'lumotdagi asosiy nom")
    private String name;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY,
            description = "Narxi")
    private Double price = 0.0;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY,
            description = "Soni")
    private Integer quantity = 0;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY,
            description = "Ikki vaqt orasidagi boshlang'ich vaqt")
    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime startTime;

    @Schema(accessMode = Schema.AccessMode.READ_WRITE,
            description = "Ikki vaqt orasidagi yakuniy vaqt")
    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime endTime;


    public void plusPrice(Double price) {
        this.price += price;
    }

    public void plusQuantity(Integer quantity) {
        this.quantity += quantity;
    }

}