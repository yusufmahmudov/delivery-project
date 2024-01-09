package food.delivery.controller;

import food.delivery.telegram.TelegramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import food.delivery.dto.EmployeeDto;
import food.delivery.service.EmployeeService;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
@Tag(name = "employee", description = "Xodimlar ma'lumotlarini boshqaruvchi apilar classi")
@Validated
@PreAuthorize("hasAnyAuthority('ROLE_COURIER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final TelegramService telegramService;


    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "Barcha xodimlarni chiqarish", tags = {"employee", "get"})
    @GetMapping("/all")
    public ResponseEntity<?> allEmployee(
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset) {
        return employeeService.allEmployee(limit, offset);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "Barcha faol xodimlarni chiqarish", tags = {"employee", "get"})
    @GetMapping("/all-active")
    public ResponseEntity<?> allEmployeeIsActive(
            @RequestParam(value = "active", defaultValue = "true") Boolean active,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset) {
        return employeeService.allEmployeeIsActive(active, limit, offset);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "Barcha faol xodimlar. workplace ish o'rni", tags = {"employee", "get"})
    @GetMapping("/all-workplace")
    public ResponseEntity<?> getByActiveTrueAndWorkplace(
            @RequestParam @NotNull String workplace,
            @RequestParam(value = "active", defaultValue = "true") Boolean active,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset) {
        return employeeService.getByActiveTrueAndWorkplace(workplace, active, limit, offset);
    }


    @Operation(summary = "'id' bo'yicha 1ta xodim ma'lumotlarini chiqarish", tags = {"employee", "get"})
    @GetMapping("/by-id")
    public ResponseEntity<?> getById() {
        return employeeService.getById();
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "'id' bo'yicha 1ta xodimni o'chirish", tags = {"employee", "delete"})
    @DeleteMapping("/by-id")
    public ResponseEntity<?> deleteById(
            @RequestParam @Min(1) Integer id) {
        return employeeService.deleteById(id);
    }


    @Operation(summary = "Xodim ma'lumotlarini o'zgartirish", tags = {"employee", "put"})
    @PutMapping("/update")
    public ResponseEntity<?> update(
            @RequestBody EmployeeDto employeeDto) {
        return employeeService.update(employeeDto);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "Xodim holatini o'zgartirish. active. by-id",
            tags = {"employee", "patch"})
    @PatchMapping("/active/by-id")
    public ResponseEntity<?> setIsActive(
            @RequestParam @Min(1) Integer id,
            @RequestParam Boolean active
    ) {
        return employeeService.setIsActive(id, active);
    }


//    @PreAuthorize("hasRole('ROLE_MODERATOR')")
//    @Operation(summary = "Xodim rolelarini o'zgartirish. by-id",
//            tags = {"employee", "patch"})
//    @PatchMapping("/set-roles")
//    public ResponseEntity<?> setRoles(
//            @RequestBody  Set<String> roles,
//            @RequestParam Integer id) {
//        return employeeService.setRoles(roles, id);
//    }



    @Operation(summary = "Telegram uchun admin qo'shish",
            tags = {"telegram", "post"})
    @PostMapping("/add-admin")
    public ResponseEntity<?> addAdminForTg(
            @RequestParam Long userId,
            @RequestParam Boolean admin) {
        return telegramService.addAdminForTg(userId, admin);
    }


    @Operation(summary = "Barcha telegram adminlar",
            tags = {"telegram", "get"})
    @GetMapping("/get-all-admin")
    public ResponseEntity<?> allAdmin(
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset) {
        return telegramService.allAdmin(limit, offset);
    }


    @Operation(summary = "Xodim uchun rasm", tags = {"post", "employee"})
    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadImage(
            @RequestParam MultipartFile image
            ) {
        return employeeService.uploadImage(image);
    }

}
