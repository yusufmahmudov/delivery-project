package food.delivery.controller;

import food.delivery.dto.EmployeeDto;
import food.delivery.dto.EmployeeRole;
import food.delivery.dto.ProductDto;
import food.delivery.dto.UserDto;
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
            @RequestBody EmployeeRole employeeRole) {
        return authService.roleForEmployee(employeeRole);
    }


    @Operation(summary = "Xodim uchun. Telefon raqam yuboriladi",
            tags = {"auth", "post"})
    @PostMapping("/sign/employee")
    public ResponseEntity<?> createEmployeeAccount(
            @RequestBody @NotNull EmployeeDto employeeDto) {
        return authService.createEmployeeAccount(employeeDto);
    }


    @Operation(summary = "Xodim uchun Login. Telefon raqam va code yuboriladi. token qaytaradi",
            tags = {"auth", "post"})
    @PostMapping("send-code/employee")
    public ResponseEntity<?> loginEmployeeCheckCode(
            @RequestBody @NotNull EmployeeDto employeeDto) {
        return authService.loginEmployeeCheckCode(employeeDto);
    }


    @Operation(summary = "User uchun. Telefon raqam yuboriladi",
            tags = {"auth", "post"})
    @PostMapping("/sign/user")
    public ResponseEntity<?> createAccount(
            @RequestBody @NotNull UserDto userDto
    ) {
        return authService.createUserAccount(userDto);
    }


    @Operation(summary = "User uchun Login. telefon raqam va code yuboriladi. token qaytaradi",
            tags = {"auth", "post"})
    @PostMapping("/send-code/user")
    public ResponseEntity<?> loginUserCheckCode(
            @RequestBody @NotNull UserDto userDto) {
        return authService.loginUserCheckCode(userDto);
    }


    @Operation(summary = "Telegram botdan webapp orqali kiruvchi api", tags = {"auth", "get"})
    @GetMapping("/login")
    public ResponseEntity<?> getLogin(
            @RequestParam Long chatId
    ) {
        return productService.allProducts(10, 0);
    }

}