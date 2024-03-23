package food.delivery.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import food.delivery.helper.AppMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Schema(name = "OrderDto", description = "Buyurtma ma'lumotlarini saqlovchi class")
public class OrderDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;


    @Schema(description = "Buyurtma egasini 'userId'. UserDto classining 'id'si",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long userId;


    @Schema(description = "Buyurtma qilishchi xodim idsi. EmployeeDto classining 'id'si",
            accessMode = Schema.AccessMode.READ_WRITE)
    private Integer employeeId;


    @Schema(description = "Buyurtma uchun to'lov oluvchi kassir idsi.",
            accessMode = Schema.AccessMode.READ_WRITE)
    private Integer cashierId;


    @Schema(description = "Buyurtmani yetqazib berivchi kuryer 'kuryerId'. EmployeeDto classining 'id'si",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Integer courierId;


    @Schema(description = "Buyurtmani qabul qilgan filial idsi.",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Integer filialId;


    @Schema(description = "Buyurtma raqami",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String orderNumber;


    @Schema(description = "Stol raqami",
            accessMode = Schema.AccessMode.READ_WRITE)
    private Integer tableNumber;


    @Schema(description = "Buyurtma to'lovi amalga oshgan bo'lsa true, aks holda false",
            accessMode = Schema.AccessMode.READ_WRITE)
    @NotNull(message = AppMessages.EMPTY_FIELD)
    private Boolean paid;


    @Schema(description = "To'lov turi. Naqd, payme yoki boshqa to'lov turi",
            accessMode = Schema.AccessMode.READ_WRITE)
    @NotNull(message = AppMessages.EMPTY_FIELD)
    private String paymentType;


    @Schema(description = "Buyurtma turi. Olib ketish, yetqazib berish, xodim buyurtma yaratish",
            accessMode = Schema.AccessMode.READ_WRITE)
    @NotNull(message = AppMessages.EMPTY_FIELD)
    private String orderType;


    @Schema(description = "User location saqlovchi id, yetqazib berishda berish majburiy",
            accessMode = Schema.AccessMode.READ_WRITE)
    private Long locationId;


    @Schema(description = "Yetqazib berish uchun to'lov miqdori. DeliveryPrice classidan olinadi",
            accessMode = Schema.AccessMode.READ_ONLY)
    @DecimalMin(value = "0.0", message = "deliveryPrice value min = 0.0")
    private Double deliveryPrice;


    @Schema(description = "Buyurtma qilingan masulotlarni umumiy narxi",
            accessMode = Schema.AccessMode.READ_ONLY)
    @DecimalMin(value = "0.0", message = "totalPrice value min = 0.0")
    private Double totalPrice;


    @Schema(description = "Obsuluga, Xodim xizmat ko'rsatishining naxri")
    @DecimalMin(value = "0.0", message = "totalPrice value min = 0.0")
    private Double servicingPrice;


    @Schema(description = "Buyurtma qilingan mahsulotlar umumiy soni",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Integer quantity;


    @Schema(description = "Foydalanuvchi yuborgan xabar, izoh",
            accessMode = Schema.AccessMode.READ_WRITE)
    private String comment;


    @Schema(description = "Admin tomonidan bekor qilinganligi izohi",
            accessMode = Schema.AccessMode.READ_WRITE)
    private String notice;


    @Schema(description = "Order holati",
            accessMode = Schema.AccessMode.READ_WRITE)
    private String status;


    @Schema(description = "Buyurtma rasmiylashtirish vaqti",
            accessMode = Schema.AccessMode.READ_WRITE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private String orderTime;


    @Schema(description = "Buyurtma qilingan vaqt",
            accessMode = Schema.AccessMode.READ_ONLY)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private String createdAt;


    @Schema(description = "Buyurtma yopilgan vaqt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private String orderCompletedTime;


    @Schema(description = "Buyurtma mahsulotlari ro'yxati",
            accessMode = Schema.AccessMode.READ_WRITE, required = true)
    private List<OrderedProductDto> orderProducts;

}