package food.delivery.service;

import food.delivery.dto.*;
import food.delivery.dto.response.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    ResponseDto<List<UserDto>> allUser();

    ResponseDto<UserDto> getById();

    ResponseDto<String> update(UserDto userDto);


}