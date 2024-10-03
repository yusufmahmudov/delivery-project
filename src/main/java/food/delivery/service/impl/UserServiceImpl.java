package food.delivery.service.impl;

import food.delivery.dto.*;
import food.delivery.dto.response.GetResponse;
import food.delivery.dto.response.ValidatorDto;
import food.delivery.helper.AppMessages;
import food.delivery.model.Role;
import food.delivery.model.User;
import food.delivery.repository.RoleRepository;
import food.delivery.repository.UserRepository;
import food.delivery.security.SecurityUtil;
import food.delivery.service.ImageService;
import food.delivery.service.UserService;
import food.delivery.service.ValidatorService;
import food.delivery.service.mapper.interfaces.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final ValidatorService validatorService;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final ImageService imageService;
    @Value("${main.domain}")
    private String domain;



    @Override
    public ResponseEntity<?> allUser(Integer limit, Integer offset) {
        try {
            List<UserDto> userDtos = userRepository.findAll()
                    .stream().map(userMapper::toDto).toList();

            List<UserDto> result = new ArrayList<>();

            GetResponse response = new GetResponse();
            response.setCount(0);
            response.setPrevious(domain + "/user/all/?limit="
                    +limit+"&offset=0");
            response.setData(result);

            if (userDtos.size() <= offset) {
                return ResponseEntity.ok().body(response);
            }

            for (int i = offset; i < offset+limit; i++) {
                result.add(userDtos.get(i));
                if (userDtos.size()-1 == i) break;
            }

            response.setCount(result.size());
            response.setData(result);
            response.setNext(userDtos.size() >= offset+limit?domain + "/user/all/?" +
                    "limit="+limit+"&offset="+(offset+limit):null);
            response.setPrevious(domain + "/user/all/?" +
                    "limit="+limit+"&offset="+(Math.max(offset-limit, 0)));

            return ResponseEntity.ok().body(response);
        } catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> getMe() {
        try {
            Long id = SecurityUtil.getUserDto().getId();
            Optional<User> optional = userRepository.findById(id);
            if (optional.isEmpty()){
                return ResponseEntity.internalServerError().body(AppMessages.NOT_FOUND);
            }
            UserDto userDto = userMapper.toDto(optional.get());
            Role role = roleRepository.findById(1).get();
            Set<String> r = new HashSet<>();
            r.add(role.getName());
            userDto.setRole(r);
//            userDto.setUsername(userDto.getUsername().substring(5));

            return ResponseEntity.ok().body(userDto);
        }catch (NoSuchElementException | NullPointerException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
/*

            List<EmployeeRoles> employeeRoles = employeeRolesRepository
                    .findByEmployeeId(id);
            Set<String> r = new HashSet<>();

            for (EmployeeRoles roles : employeeRoles) {
                Integer roleId = roles.getRoleId();
                Role role = roleRepository.findById(roleId).get();
                r.add(role.getName());
            }

            employeeDto.setRole(r);

            return ResponseEntity.ok().body(employeeDto);
*/

    @Override
    public ResponseEntity<?> update(UserDto userDto) {
        try {
            if (userDto.getId() == null) {
                return ResponseEntity.ok().body("Id " + AppMessages.EMPTY_FIELD);
            }

            List<ValidatorDto> errors = validatorService.validateUser(userDto);
            if (!errors.isEmpty()) {
                return ResponseEntity.internalServerError().body(AppMessages.VALIDATOR_MESSAGE);
            }
            Long id = SecurityUtil.getUserDto().getId();
            User user = userRepository.findById(id).get();

//            if (userDto.getUsername() != null) {
//                String username = "user_" + userDto.getUsername();
//                if (userRepository.existsByUsername(username)) {
//                    return ResponseDto.<String>builder()
//                            .code(AppCode.DATABASE_ERROR)
//                            .success(false)
//                            .message("Error: Username is already taken!")
//                            .build();
//                }
//                user.setUsername(username);
//            }
//            if (userDto.getPassword() != null) {
//                String password = passwordEncoder.encode(userDto.getPassword());
//                user.setPassword(password);
//            }

            user.setFullName(userDto.getFullName() == null ? user.getFullName() : userDto.getFullName());
            user.setPhoneNum1(userDto.getPhoneNum1() == null ? user.getPhoneNum1() : userDto.getPhoneNum1());
            user.setPhoneNum2(userDto.getPhoneNum2() == null ? user.getPhoneNum2() : userDto.getPhoneNum2());

            userRepository.save(user);

            return ResponseEntity.ok().body(userDto);
        } catch (NullPointerException | IllegalArgumentException | DataAccessException | NoSuchElementException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


}
