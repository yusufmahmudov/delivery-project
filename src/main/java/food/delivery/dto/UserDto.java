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

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Schema(name = "UserDto", description = "Foydalanuvchi ma'lumotlarini o'zida saqlovchi class")
public class UserDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;


    @Schema(description = "Foydalanuvchini to'liq ismi",
            accessMode = Schema.AccessMode.READ_WRITE, required = true)
    @NotNull(message = AppMessages.EMPTY_FIELD)
    @Pattern(regexp="^[a-zA-Z]+$", message = "Faqat katta va kichik harflardan iborat bo'lishi kerak")
    @Size(min = 3, max = 130, message = "length min 3, max 130")
    private String fullName;


//    @Schema(description = "Foydalanuvchini nomi")
//    @NotNull(message = AppMessages.EMPTY_FIELD)
//    @Size(min = 5, max = 30, message = "length min 5 and max 30")
//    @Pattern(regexp="^(?!\\d)[a-zA-Z\\d]*$",
//            message="Katta va kichik harf, sonlardan iborat bo'lishi shart")
//    private String username;
//
//
//    @Schema(description = "Foydalanuvchini paroli",
//            accessMode = Schema.AccessMode.WRITE_ONLY)
//    @NotNull(message = AppMessages.EMPTY_FIELD)
//    @Size(min = 8, max = 20, message = "length min 8 and max 20")
//    @Pattern(regexp="^[a-zA-Z0-9]+$",
//            message="Faqat katta, kichik harflar va sonlardan iborat bo'lishi shart")
//    private String password;


    @Schema(description = "Foydalanuvchini paroli",
            accessMode = Schema.AccessMode.WRITE_ONLY)
    @Pattern(regexp="^[0-9]+$",
            message="Telefon raqamni tasdiqlovchi code")
    private String toolWord;


    @Schema(description = "Foydalanuvchini asosiy telefon raqami",
            accessMode = Schema.AccessMode.READ_WRITE)
    @NotNull(message = AppMessages.EMPTY_FIELD)
    @Size(max = 20, message = "length max 20")
    @Pattern(regexp = "^([+]?\\d{3}[-\\s]?|)\\d{2}[-\\s]?\\d{3}[-\\s]?\\d{2}[-\\s]?\\d{2}$",
            message = "Invalid phone number")
    private String phoneNum1;


    @Schema(description = "Foydalanuvchini qo'shimcha raqami",
            accessMode = Schema.AccessMode.READ_WRITE)
    @Size(max = 20, message = "length max 20")
    @Pattern(regexp = "^([+]?\\d{3}[-\\s]?|)\\d{2}[-\\s]?\\d{3}[-\\s]?\\d{2}[-\\s]?\\d{2}$",
            message = "Invalid phone number")
    private String phoneNum2;


    @Schema(description = "Xodimning jinsi",
            accessMode = Schema.AccessMode.READ_WRITE)
    private String gender;


    @Schema(description = "Userning tug'ilgan sanasi",
            accessMode = Schema.AccessMode.READ_WRITE, required = true,
            example = "dd-MM-yyyy")
    @JsonFormat(pattern="dd-MM-yyyy")
    private String birthDate;


    @Schema(description = "Foydalanuvchini telegram username",
            accessMode = Schema.AccessMode.READ_WRITE)
    @NotNull(message = AppMessages.EMPTY_FIELD)
    private String tg_username;


    @Schema(description = "Foydalanuvchini telegram idsi",
            accessMode = Schema.AccessMode.READ_WRITE)
    @NotNull(message = AppMessages.EMPTY_FIELD)
    private Long tg_id;


    @Schema(description = "Foydalanuvchi qo'shilgan vaqt",
            accessMode = Schema.AccessMode.READ_ONLY)
    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private String createdAt;


    @Schema(description = "Foydalanuvchi tili. uz - ru - en")
    private String languageCode;


    @Schema(description = "Telegram uchun admin")
    private Boolean isAdmin;


    @Schema(description = "null or user = USER_ROLE",
            accessMode = Schema.AccessMode.READ_WRITE)
    private Set<String> role;

}