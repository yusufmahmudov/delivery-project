package food.delivery.controller;

import food.delivery.dto.EmployeeRole;
import food.delivery.dto.ProductDto;
import food.delivery.service.AuthService;
import food.delivery.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Tag(name = "auth", description = "Xodim va userlar uchun SIGN va LOGIN")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;
    private final ProductService productService;


//    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "MODERATOR xodimlar uchun role belgilaydi, default employee. " +
            "admin, mod, courier", tags = {"auth", "patch"})
    @PatchMapping("/role-employee")
    public ResponseEntity<?> roleForEmployee(
            @RequestParam("abuAsbob") @NotNull EmployeeRole employeeRole) {
        return authService.roleForEmployee(employeeRole);
    }


    @Operation(summary = "Xodim uchun. Telefon raqam yuboriladi",
            tags = {"auth", "post"})
    @PostMapping("/sign/employee")
    public ResponseEntity<?> createEmployeeAccount(
            @RequestParam @NotNull String phone) {
        return authService.createEmployeeAccount(phone);
    }


    @Operation(summary = "Xodim uchun Login. Telefon raqam va code yuboriladi. token qaytaradi",
            tags = {"auth", "post"})
    @PostMapping("send-code/employee")
    public ResponseEntity<?> loginEmployeeCheckCode(
            @RequestParam @NotNull String phone,
            @RequestParam @NotNull String code) {
        return authService.loginEmployeeCheckCode(phone, code);
    }


    @Operation(summary = "User uchun. Telefon raqam yuboriladi",
            tags = {"auth", "post"})
    @PostMapping("/sign/user")
    public ResponseEntity<?> createAccount(
            @RequestParam @NotNull String phone,
            @RequestParam Long tgId
    ) {
        return authService.createUserAccount(phone, tgId);
    }


    @Operation(summary = "User uchun Login. telefon raqam va code yuboriladi. token qaytaradi",
            tags = {"auth", "post"})
    @PostMapping("/send-code/user")
    public ResponseEntity<?> loginUserCheckCode(
            @RequestParam @NotNull String phone,
            @RequestParam @NotNull String code) {
        return authService.loginUserCheckCode(phone, code);
    }


    @Operation(summary = "Telegram botdan webapp orqali kiruvchi api", tags = {"auth", "get"})
    @GetMapping("/login")
    public ResponseEntity<?> getLogin(
            @RequestParam Long chatId
    ) {
        List<ProductDto> products = productService.allProducts().getData();
        return ResponseEntity.ok().body("product");
    }

}