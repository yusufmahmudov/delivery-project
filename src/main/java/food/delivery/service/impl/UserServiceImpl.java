package food.delivery.service.impl;

import food.delivery.dto.*;
import food.delivery.dto.response.ResponseDto;
import food.delivery.dto.response.ValidatorDto;
import food.delivery.dto.template.ImageDto;
import food.delivery.helper.AppCode;
import food.delivery.helper.AppMessages;
import food.delivery.model.Image;
import food.delivery.model.User;
import food.delivery.repository.UserRepository;
import food.delivery.security.SecurityUtil;
import food.delivery.service.ImageService;
import food.delivery.service.UserService;
import food.delivery.service.ValidatorService;
import food.delivery.service.mapper.interfaces.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final ValidatorService validatorService;

    private final PasswordEncoder passwordEncoder;

    private final ImageService imageService;


    @Override
    public ResponseDto<List<UserDto>> allUser() {
        try {
            List<UserDto> userDtos = userRepository.findAll()
                    .stream().map(userMapper::toDto).toList();

            return ResponseDto.<List<UserDto>>builder()
                    .data(userDtos)
                    .code(AppCode.OK)
                    .message(AppMessages.OK)
                    .success(true)
                    .build();
        }catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseDto.<List<UserDto>>builder()
                    .code(AppCode.ERROR)
                    .message(e.getMessage())
                    .success(false)
                    .build();
        }
    }


    @Override
    public ResponseDto<UserDto> getById() {
        try {
            Long id = SecurityUtil.getUserDto().getId();
            Optional<User> optional = userRepository.findById(id);
            if (optional.isEmpty()){
                return ResponseDto.<UserDto>builder()
                        .success(false)
                        .message(AppMessages.NOT_FOUND)
                        .code(AppCode.NOT_FOUND)
                        .build();
            }
            UserDto userDto = userMapper.toDto(optional.get());
//            userDto.setUsername(userDto.getUsername().substring(5));

            return ResponseDto.<UserDto>builder()
                    .success(true)
                    .data(userDto)
                    .message(AppMessages.OK)
                    .code(AppCode.OK)
                    .build();
        }catch (NoSuchElementException | NullPointerException e) {
            log.error(e.getMessage());
            return ResponseDto.<UserDto>builder()
                    .success(false)
                    .message(e.getMessage())
                    .code(AppCode.ERROR)
                    .build();
        }
    }

    @Override
    public ResponseDto<String> update(UserDto userDto) {
//        try {
//            if (userDto.getId() == null) {
//                return ResponseDto.<UserDto>builder()
//                        .success(false)
//                        .message("Id " + AppMessages.EMPTY_FIELD)
//                        .code(AppCode.ERROR)
//                        .build();
//            }

//            List<ValidatorDto> errors = validatorService.validateUser(userDto);
//            if (!errors.isEmpty()) {
//                return ResponseDto.<String>builder()
//                        .code(AppCode.VALIDATOR_ERROR)
//                        .errors(errors)
//                        .message(AppMessages.VALIDATOR_MESSAGE)
//                        .success(false)
//                        .build();
//            }
//            Long id = SecurityUtil.getUserDto().getId();
//            User user = userRepository.findById(id).get();
//
////            if (userDto.getUsername() != null) {
////                String username = "user_" + userDto.getUsername();
////                if (userRepository.existsByUsername(username)) {
////                    return ResponseDto.<String>builder()
////                            .code(AppCode.DATABASE_ERROR)
////                            .success(false)
////                            .message("Error: Username is already taken!")
////                            .build();
////                }
////                user.setUsername(username);
//            }
////            if (userDto.getPassword() != null) {
////                String password = passwordEncoder.encode(userDto.getPassword());
////                user.setPassword(password);
////            }
////
////            user.setFullName(userDto.getFullName() == null ? user.getFullName() : userDto.getFullName());
////            user.setPhoneNum1(userDto.getPhoneNum1() == null ? user.getPhoneNum1() : userDto.getPhoneNum1());
////            user.setPhoneNum2(userDto.getPhoneNum2() == null ? user.getPhoneNum2() : userDto.getPhoneNum2());
//
////            userRepository.save(user);
//
//            return ResponseDto.<String>builder()
//                    .success(true)
//                    .message(AppMessages.SAVED)
//                    .code(AppCode.OK)
//                    .build();
//        }catch (NullPointerException | IllegalArgumentException | DataAccessException | NoSuchElementException e) {
//            log.error(e.getMessage());
//            return ResponseDto.<String>builder()
//                    .success(false)
//                    .message(e.getMessage())
//                    .code(AppCode.ERROR)
//                    .build();
//        }
        return null;
    }


    @Override
    public ResponseEntity<?> uploadImage(MultipartFile multipartFile) {
        Long id = SecurityUtil.getUserDto().getId();
        User user = userRepository.findById(id).get();

//        Long imageId = user.getImageId();

        ImageDto image = (ImageDto) imageService
                .addImage(multipartFile, "user image").getBody();

//        assert image != null;
//        user.setImagePath(image.getImagePath());
//        user.setImageId(image.getId());
//
//        userRepository.save(user);
//
//        if (imageId != null) {
//            boolean check = (boolean) imageService.deleteImage(imageId).getBody();
//            if (!check) {
//                return ResponseEntity.internalServerError().body("Old image not deleted");
//            }
//        }

        return ResponseEntity.accepted().body(image);
    }


}
