package food.delivery.service.impl;

import food.delivery.dto.response.ResponseDto;
import food.delivery.dto.template.ImageDto;
import food.delivery.helper.AppCode;
import food.delivery.helper.AppMessages;
import food.delivery.model.Image;
import food.delivery.repository.ImageRepository;
import food.delivery.service.ImageService;
import food.delivery.service.mapper.interfaces.ImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    @Value("${image.upload.path}")
    private String uploadPath;

    @Value("${main.domain}")
    private String domain;

    private final Path root = Paths.get("images");


    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }


    @Override
    public ResponseEntity<?> addImage(MultipartFile multipartFile, String position) {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        Path filePath = root.resolve(fileName);
        String path = filePath.toString().replaceAll("\\\\", "/");

        Image image = new Image();
        image.setName(multipartFile.getOriginalFilename());
        image.setPosition(position);
        image.setImagePath(domain + "/" + path);
        image.setType(multipartFile.getContentType());
        imageRepository.save(image);

        try {
            multipartFile.transferTo(filePath);
        } catch (IOException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

        return ResponseEntity.ok().body(imageMapper.toDto(image));
    }


    @Override
    public ResponseEntity<?> getAllMainPageImage() {
        List<Image> images = imageRepository.findAllByPosition("main page image");

        if (images.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No content");
        }
        List<ImageDto> list = images.stream()
                .map(imageMapper::toDto).toList();

        return ResponseEntity.ok().body(list);
    }


    @Override
    public ResponseEntity<?> deleteImage(Long id) {
        Optional<Image> image = imageRepository.findById(id);
        if (image.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        Path file = root.resolve(image.get().getName());
        try {
            Files.deleteIfExists(file);
        } catch (IOException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body("Not deleted");
        }
        imageRepository.deleteById(id);

        return ResponseEntity.accepted().body(true);
    }


    @Override
    public ResponseDto<byte[]> getImage(String fileName) {
        try {
            Optional<Image> imageData = imageRepository.findByName(fileName);
            String filePath = imageData.get().getImagePath();
            byte[] image = Files.readAllBytes(new File(filePath).toPath());

            return ResponseDto.<byte[]>builder()
                    .data(image)
                    .code(AppCode.OK)
                    .message(AppMessages.OK)
                    .success(true)
                    .build();
        }catch (Exception e) {
            log.error(e.getMessage());
            return ResponseDto.<byte[]>builder()
                    .code(AppCode.ERROR)
                    .message(e.getMessage())
                    .success(false)
                    .build();
        }
    }


    @Override
    public ResponseDto<byte[]> getImageByProductId(Integer productId) {
        try {
//            Optional<Image> imageData = imageRepository.findByProductId(productId);
//            String filePath = imageData.get().getImagePath();
//            byte[] image = Files.readAllBytes(new File(filePath).toPath());

            return ResponseDto.<byte[]>builder()
//                    .data(image)
                    .code(AppCode.OK)
                    .message(AppMessages.OK)
                    .success(true)
                    .build();
        }catch (Exception e) {
            log.error(e.getMessage());
            return ResponseDto.<byte[]>builder()
                    .code(AppCode.ERROR)
                    .message(e.getMessage())
                    .success(false)
                    .build();
        }
    }

}