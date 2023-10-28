package food.delivery.service;


import food.delivery.dto.FilialDto;
import food.delivery.dto.response.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public interface FilialService {

    ResponseEntity<?> addFilial(FilialDto filialDto);

    ResponseEntity<?> allFilial(Integer limit, Integer offset);

    ResponseEntity<?> allFilialIsActive(Boolean active, Integer limit, Integer offset);

    ResponseEntity<?> allOpenFilial(Integer limit, Integer offset);

    ResponseEntity<?> sortingByDistance(Double lat, Double lon, Integer limit, Integer offset);

    ResponseEntity<?> getById(Integer id);

    ResponseEntity<?> updateFilial(FilialDto filialDto);

    ResponseEntity<?> deleteFilial(Integer id);

    ResponseEntity<?> isActive(Integer id, Boolean active);

    SortedMap<Double, FilialDto> checkTheDistanceForOrder(Double lat, Double lon);

    ResponseEntity<?> uploadImage(MultipartFile multipartFile, Integer filialId);


}
