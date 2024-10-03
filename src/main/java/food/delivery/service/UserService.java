package food.delivery.service;

import food.delivery.dto.*;
import food.delivery.dto.response.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    ResponseEntity<?> allUser(Integer limit, Integer offset);

    ResponseEntity<?> getMe();

    ResponseEntity<?> update(UserDto userDto);


}