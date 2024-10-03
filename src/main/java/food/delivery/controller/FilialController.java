package food.delivery.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import food.delivery.dto.FilialDto;
import food.delivery.service.FilialService;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.SortedMap;

@RestController
@RequestMapping("/filial")
@RequiredArgsConstructor
@Tag(name = "filial", description = "Filial ma'lumotlari bilan ishlovchi apilar")
@Validated
//@PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
public class FilialController {

    private final FilialService filialService;


    @Operation(summary = "Yangi Filial qo'shish", tags = {"filial", "post"})
    @PostMapping("/add")
    public ResponseEntity<?> addFilial(
            @Valid @RequestBody FilialDto filialDto) {
        return filialService.addFilial(filialDto);
    }


    @Operation(summary = "Barcha filiallarni chiqarish", tags = {"filial", "get"})
    @GetMapping("/all")
    public ResponseEntity<?> allFilial(
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset
    ) {
        return filialService.allFilial(limit, offset);
    }


    @Operation(summary = "Barcha filiallarni chiqarish. Active true or false",
            tags = {"filial", "get"})
    @GetMapping("/all-active")
    public ResponseEntity<?> allFilialIsActive(
            @RequestParam(value = "active", defaultValue = "true") Boolean active,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset
    ) {
        return filialService.allFilialIsActive(active, limit, offset);
    }


    @Operation(summary = "Ayni vaqtdagi barcha ochiq filiallar",
            tags = {"filial", "get"})
    @GetMapping("/all-open")
    public ResponseEntity<?> allOpenFilial(
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset
    ) {
        return filialService.allOpenFilial(limit, offset);
    }


    @Operation(summary = "Userning lat, lon bo'yicha filiallarni sort qilib chiqarish")
    @GetMapping("/all-sorting")
    public ResponseEntity<?> sortingByDistance(
            @RequestParam(value = "lat") Double lat,
            @RequestParam(value = "lon") Double lon,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset
    ) {
        return filialService.sortingByDistance(lat, lon, limit, offset);
    }


    @Operation(summary = "'id'si bo'yicha 1ta filialni chiqarish",
            tags = {"filial", "get"})
    @GetMapping("/by-id")
    public ResponseEntity<?> getById(
            @RequestParam Integer id) {

        return filialService.getById(id);
    }


    // TODO: Update ma'lumotlarini RequestParamda jo'natish???
    @Operation(summary = "Filial ma'lumotlarini o'zgartish",
            tags = {"filial", "put"})
    @PutMapping("/update")
    public ResponseEntity<?> updateFilial(
            @RequestBody FilialDto filialDto) {
        return filialService.updateFilial(filialDto);
    }


    @Operation(summary = "'id'si bo'yicha Filialni o'chirib tashlash",
            tags = {"filial", "delete"})
    @DeleteMapping("/by-id")
    public ResponseEntity<?> deleteFilial(
            @RequestParam @Min(1) Integer id) {
        return filialService.deleteFilial(id);
    }


    @Operation(summary = "Filialni faol holatga keltirish, yoki aksi",
            tags = {"filial", "patch"})
    @PatchMapping("/active")
    public ResponseEntity<?> isActive(
            @RequestParam @Min(1) Integer id,
            @RequestParam Boolean active
    ) {
        return filialService.isActive(id, active);
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