package food.delivery.controller;

import food.delivery.dto.EmployeeDto;
import food.delivery.dto.EmployeeRole;
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

@Tag(name = "auth", description = "Xodim va userlar uchun SIGN va LOGIN")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;
    private final ProductService productService;


    @Operation(summary = "Register super admin")
    @PostMapping("/super-admin")
    public ResponseEntity<?> superAdmin(
            @RequestBody @NotNull EmployeeDto employeeDto) {
        return authService.superAdmin(employeeDto);
    }


    @Operation(summary = "Register uchun api", tags = {"auth", "post"})
    @PostMapping("/register")
    public ResponseEntity<?> registerEmployee(
            @RequestBody @NotNull EmployeeDto employeeDto) {
        return authService.registerEmployee(employeeDto);
    }


    //    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "MODERATOR xodimlar uchun role belgilaydi. " +
            "admin, mod, employee", tags = {"auth", "patch"})
    @PatchMapping("/role-employee")
    public ResponseEntity<?> roleForEmployee(
            @RequestBody EmployeeRole employeeRole) {
        return authService.roleForEmployee(employeeRole);
    }


    @Operation(summary = "Xodim uchun Login. Telefon raqam va password yuboriladi. token qaytaradi",
            tags = {"auth", "post"})
    @PostMapping("login/employee")
    public ResponseEntity<?> loginEmployee(
            @RequestBody @NotNull EmployeeDto employeeDto) {
        return authService.loginEmployee(employeeDto);
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


//    @Operation(summary = "Xodim uchun. Telefon raqam yuboriladi",
//            tags = {"auth", "post"})
//    @PostMapping("/sign/employee")
//    public ResponseEntity<?> createEmployeeAccount(
//            @RequestBody @NotNull EmployeeDto employeeDto) {
//        return authService.createEmployeeAccount(employeeDto);
//    }

}