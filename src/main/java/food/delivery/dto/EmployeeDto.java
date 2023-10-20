package food.delivery.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import food.delivery.helper.AppMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Schema(name = "EmployeeDto",
        description = "Xodimlar ma'lumotlarini saqlovchi class. " +
                      "ADMIN, USER, MODERATOR, COURIER")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class EmployeeDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "Xodim ismi",
            accessMode = Schema.AccessMode.READ_WRITE)
    @Size(min = 3, max = 30, message = "length min 3 and max 30")
//    @NotNull(message = AppMessages.EMPTY_FIELD)
    @Pattern(regexp="^[a-zA-Z]+$", message = "Faqat katta va kichik harflardan iborat bo'lishi kerak")
    private String firstName;


    @Schema(description = "Xodim familiyasi",
            accessMode = Schema.AccessMode.READ_WRITE)
    @Size(min = 3, max = 30, message = "length min 3 and max 30")
//    @NotNull(message = AppMessages.EMPTY_FIELD)
    @Pattern(regexp="^[a-zA-Z]+$", message = "Faqat katta va kichik harflardan iborat bo'lishi kerak")
    private String lastName;


    @Schema(description = "Xodimni asosiy telefon raqami",
            accessMode = Schema.AccessMode.READ_WRITE, required = true)
    @Size(min = 5, max = 20, message = "length min 5 and max 20")
    @NotNull(message = AppMessages.EMPTY_FIELD)
    @Pattern(regexp = "^([+]?\\d{3}[-\\s]?|)\\d{2}[-\\s]?\\d{3}[-\\s]?\\d{2}[-\\s]?\\d{2}$",
            message = "Invalid phone number")
    private String phoneNum1;


    @Schema(description = "Xodimni qo'shimcha telefon raqami",
            accessMode = Schema.AccessMode.READ_WRITE)
    @Size(min = 5, max = 20, message = "length min 5 and max 20")
//    @NotNull(message = AppMessages.EMPTY_FIELD)
    @Pattern(regexp = "^([+]?\\d{3}[-\\s]?|)\\d{2}[-\\s]?\\d{3}[-\\s]?\\d{2}[-\\s]?\\d{2}$",
            message = "Invalid phone number")
    private String phoneNum2;


    @Schema(description = "Xodimni yashash manzili ma'lumotlari",
            accessMode = Schema.AccessMode.READ_WRITE)
    @Size(max = 120, message = "length max 20")
//    @NotNull(message = AppMessages.EMPTY_FIELD)
    private String address;


    @Schema(description = "Xodim faol bo'lsa true, aks holda false",
            accessMode = Schema.AccessMode.READ_WRITE, defaultValue = "false")
    private Boolean active;


    @Schema(description = "Xodimning jinsi",
            accessMode = Schema.AccessMode.READ_WRITE)
    private String gender;


    @Schema(description = "code")
    private String code;


    @Schema(description = "Xodimning ish o'rni. ADMIN, COURIER",
            accessMode = Schema.AccessMode.READ_WRITE)
    @Size(max = 20, message = "length max 20")
//    @NotNull(message = AppMessages.EMPTY_FIELD)
    @Pattern(regexp="^[a-zA-Z]+$",
            message="Faqat katta, kichik harflardan iborat bo'lishi shart")
    private String workplace;


    @Schema(description = "Xodimning oylik maoshi",
            accessMode = Schema.AccessMode.READ_WRITE)
//    @NotNull(message = AppMessages.EMPTY_FIELD)
    private Double salary;


    @Schema(description = "Passport seriya raqami",
            accessMode = Schema.AccessMode.READ_ONLY,
            example = "AA 1112233")
    @Size(max = 20, message = "length max 20")
//    @NotNull(message = AppMessages.EMPTY_FIELD)
    @Pattern(regexp="^[A-Z0-9]+$",
            message="Faqat katta harflar va sonlardan iborat bo'lishi shart")
    private String passportNo;


    @Schema(description = "Xodimning tug'ilgan sanasi",
            accessMode = Schema.AccessMode.READ_WRITE,
            example = "dd-MM-yyyy")
    @JsonFormat(pattern="dd-MM-yyyy")
    private String birthDate;


    @Schema(description = "Xodim qo'shilgan vaqt",
            accessMode = Schema.AccessMode.READ_ONLY)
    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private String createdAt;


    @Schema(description = "Foydalanuvchini rasmi saqlanuvchi path",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String imagePath;


    @Schema(description = "MODERATOR = mod, ADMIN = admin, COURIER = courier, EMPLOYEE = employee",
            accessMode = Schema.AccessMode.READ_WRITE)
//    @NotNull(message = AppMessages.EMPTY_FIELD)
    private Set<String> role;

}
