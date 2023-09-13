package food.delivery.service;


import food.delivery.dto.FilialDto;
import food.delivery.dto.response.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public interface FilialService {

    ResponseDto<String> addFilial(FilialDto filialDto);

    ResponseDto<List<FilialDto>> allFilial();

    ResponseDto<List<FilialDto>> allFilialIsActive(Boolean active);

    ResponseDto<FilialDto> getById(Integer id);

    ResponseDto<FilialDto> updateFilial(FilialDto filialDto);

    ResponseDto<String> deleteFilial(Integer id);

    ResponseDto<String> isActive(Integer id, Boolean active);

    SortedMap<Double, FilialDto> checkTheDistance(Double lat, Double lon);

    ResponseDto<List<FilialDto>> allOpenFilial();

    ResponseEntity<?> uploadImage(MultipartFile multipartFile, Integer filialId);


}
