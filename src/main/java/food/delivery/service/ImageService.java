package food.delivery.service;

import food.delivery.dto.response.ResponseDto;
import food.delivery.model.Image;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void init();

    ResponseEntity<?> addImage(MultipartFile multipartFile, String position);

    ResponseEntity<?> getAllMainPageImage();

    ResponseEntity<?> deleteImage(Long id);



    ResponseDto<byte[]> getImage(String fileName);

    ResponseDto<byte[]> getImageByProductId(Integer productId);
}
