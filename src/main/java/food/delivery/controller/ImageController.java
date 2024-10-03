package food.delivery.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import food.delivery.service.ImageService;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;

@RequiredArgsConstructor
@RequestMapping("/image")
@RestController
@Tag(name = "image", description = "Asosan Product rasmlarini saqlash uchun apilar. Tashqi aloqa uchun emas")
//@PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
public class ImageController {

    private final ImageService imageService;


    @PostMapping("/add-image")
    public ResponseEntity<?> addImage(
            @RequestParam(name = "image") MultipartFile multipartFile,
            @RequestParam @NotNull String position) {
        return imageService.addImage(multipartFile, position);
    }


    @GetMapping("/get-main")
    public ResponseEntity<?> getAllMainPageImage() {
        return imageService.getAllMainPageImage();
    }


    @DeleteMapping("/delete-by-id")
    public ResponseEntity<?> deleteImage(
            @RequestParam @Min(1) Long id) {
        return imageService.deleteImage(id);
    }


    @Operation(summary = "Fayl nomi orqali chiqarish", tags = {"image"})
    @GetMapping("/by-name")
    public ResponseEntity<?> getImage(
            @RequestParam("name") String fileName) {

        byte[] image = imageService.getImage(fileName).getData();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_PNG)
                .body(image);
    }


    @Operation(summary = "Product 'id'si orqali rasmni chiqarish", tags = {"image"})
    @GetMapping("/by-id")
    public ResponseEntity<?> getImageByProductId(
            @RequestParam("id") Integer productId) {

        byte[] image = imageService.getImageByProductId(productId).getData();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_PNG)
                .body(image);
    }


}
