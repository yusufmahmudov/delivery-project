package food.delivery.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import food.delivery.dto.FilialDto;
import food.delivery.dto.response.ResponseDto;
import food.delivery.service.FilialService;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/filial")
@RequiredArgsConstructor
@Tag(name = "filial", description = "Filial ma'lumotlari bilan ishlovchi apilar")
@Validated
@PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
public class FilialController {

    private final FilialService filialService;


    @Operation(summary = "Yangi Filial qo'shish", tags = {"filial", "post"})
    @PostMapping("/add")
    public ResponseDto<String> addFilial(
            @Valid @RequestBody FilialDto filialDto) {
        return filialService.addFilial(filialDto);
    }


    @Operation(summary = "Barcha filiallarni chiqarish", tags = {"filial", "get"})
    @GetMapping("/all")
    public ResponseDto<List<FilialDto>> allFilial() {
        return filialService.allFilial();
    }


    @Operation(summary = "Barcha filiallarni chiqarish. Active true or false",
            tags = {"filial", "get"})
    @GetMapping("/all-active")
    public ResponseDto<List<FilialDto>> allFilialIsActive(
            @RequestParam Boolean active) {

        return filialService.allFilialIsActive(active);
    }


    @Operation(summary = "Ayni vaqtdagi barcha ochiq filiallar",
            tags = {"filial", "get"})
    @GetMapping("/all-open")
    public ResponseDto<List<FilialDto>> allOpenFilial() {

        return filialService.allOpenFilial();
    }


    @Operation(summary = "'id'si bo'yicha 1ta filialni chiqarish",
            tags = {"filial", "get"})
    @GetMapping("/by-id")
    public ResponseDto<FilialDto> getById(
            @RequestParam Integer id) {

        return filialService.getById(id);
    }


    // TODO: Update ma'lumotlarini RequestParamda jo'natish???
    @Operation(summary = "Filial ma'lumotlarini o'zgartish",
            tags = {"filial", "put"})
    @PutMapping("/update")
    public ResponseDto<FilialDto> updateFilial(
            @RequestBody FilialDto filialDto) {
        return filialService.updateFilial(filialDto);
    }


    @Operation(summary = "'id'si bo'yicha Filialni o'chirib tashlash",
            tags = {"filial", "delete"})
    @DeleteMapping("/by-id")
    public ResponseDto<String> deleteFilial(
            @RequestParam @Min(1) Integer id) {
        return filialService.deleteFilial(id);
    }


    @Operation(summary = "Filialni faol holatga keltirish, yoki aksi",
            tags = {"filial", "patch"})
    @PatchMapping("/active")
    public ResponseDto<String> isActive(
            @RequestParam @Min(1) Integer id,
            @RequestParam Boolean active
    ) {
        return filialService.isActive(id, active);
    }


    @Operation(summary = "Barcha filiallar, user uchun",
            tags = {"filial", "get"})
    @GetMapping("/all-filial-for-user")
    public ResponseEntity<?> allFilialForUser(
            @RequestParam Double lat,
            @RequestParam Double lon) {
        Map<Double, FilialDto> map = filialService.checkTheDistance(lat, lon);
        return ResponseEntity.accepted().body(map);
    }


    @Operation(summary = "Filial uchun rasm", tags = {"post", "filial"})
    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadImage(
            @RequestParam MultipartFile image,
            @RequestParam Integer filialId
    ) {
        return filialService.uploadImage(image, filialId);
    }
}