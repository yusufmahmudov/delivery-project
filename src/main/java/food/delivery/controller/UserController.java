package food.delivery.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import food.delivery.dto.response.ResponseDto;
import food.delivery.dto.UserDto;
import food.delivery.service.UserService;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@Tag(name = "user", description = "User ma'lumotlari bilan ishlovchi apilar")
@PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public class UserController {

    private final UserService userService;


    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "Hamma userlarni chiqarish", tags = {"user", "get"})
    @GetMapping("/all")
    public ResponseEntity<?> allUser(
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset
    ) {
        return userService.allUser(limit, offset);
    }


    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR')")
    @Operation(summary = "'id' bo'yicha 1ta user ma'lumotlarni chiqarish", tags = {"user", "get"})
    @GetMapping("/by-id")
    public ResponseEntity<?> getById() {
        return userService.getById();
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "User ma'lumotini tahrirlash", tags = {"user", "put"})
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserDto userDto) {
        return userService.update(userDto);
    }


}
