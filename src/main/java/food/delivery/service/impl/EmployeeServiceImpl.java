package food.delivery.service.impl;

import food.delivery.dto.*;
import food.delivery.dto.response.ResponseDto;
import food.delivery.dto.response.ValidatorDto;
import food.delivery.dto.template.ImageDto;
import food.delivery.helper.AppCode;
import food.delivery.helper.AppMessages;
import food.delivery.model.Employee;
import food.delivery.model.Role;
import food.delivery.model.User;
import food.delivery.repository.EmployeeRepository;
import food.delivery.repository.RoleRepository;
import food.delivery.security.SecurityUtil;
import food.delivery.service.EmployeeService;
import food.delivery.service.ImageService;
import food.delivery.service.ValidatorService;
import food.delivery.service.mapper.interfaces.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

import static food.delivery.helper.AppCode.VALIDATOR_ERROR;
import static food.delivery.helper.AppMessages.VALIDATOR_MESSAGE;


@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ValidatorService validatorService;

    private final EmployeeMapper employeeMapper;

    private final RoleRepository roleRepository;

    private final ImageService imageService;


    @Override
    public ResponseDto<List<EmployeeDto>> allEmployee() {
        try {
            List<EmployeeDto> employeeDtos = new ArrayList<>();
            for (Employee employee : employeeRepository.findAll()) {
                EmployeeDto dto = employeeMapper.toDto(employee);
                employeeDtos.add(dto);
            }

            return ResponseDto.<List<EmployeeDto>>builder()
                    .message(AppMessages.OK)
                    .code(AppCode.OK)
                    .success(true)
                    .data(employeeDtos)
                    .build();
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseDto.<List<EmployeeDto>>builder()
                    .message(e.getMessage())
                    .code(AppCode.ERROR)
                    .success(false)
                    .build();
        }
    }


    @Override
    public ResponseDto<List<EmployeeDto>> allEmployeeIsActive(Boolean active) {
        try {
            List<EmployeeDto> employeeDtos = new ArrayList<>();
            for (Employee employee : employeeRepository.findAllByActive(active)) {
                EmployeeDto dto = employeeMapper.toDto(employee);
                employeeDtos.add(dto);
            }

            return ResponseDto.<List<EmployeeDto>>builder()
                    .message(AppMessages.OK)
                    .code(AppCode.OK)
                    .success(true)
                    .data(employeeDtos)
                    .build();
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseDto.<List<EmployeeDto>>builder()
                    .message(e.getMessage())
                    .code(AppCode.ERROR)
                    .success(false)
                    .build();
        }
    }


    @Override
    public ResponseDto<List<EmployeeDto>> getByActiveTrueAndWorkplace(String workplace, Boolean active){
        try {
            List<EmployeeDto> employeeDtos = new ArrayList<>();
            for (Employee employee : employeeRepository.findAllByActiveAndWorkplace(active, workplace)) {
                EmployeeDto dto = employeeMapper.toDto(employee);
                employeeDtos.add(dto);
            }

            return ResponseDto.<List<EmployeeDto>>builder()
                    .message(AppMessages.OK)
                    .code(AppCode.OK)
                    .success(true)
                    .data(employeeDtos)
                    .build();
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseDto.<List<EmployeeDto>>builder()
                    .message("")
                    .code(AppCode.ERROR)
                    .success(false)
                    .build();
        }
    }


    @Override
    public ResponseDto<EmployeeDto> getById() {
        try {
            Integer id = Math.toIntExact(SecurityUtil.getEmployeeDto().getId());
            Optional<Employee> optional = employeeRepository.findById(id);
            if (optional.isEmpty()){
                return ResponseDto.<EmployeeDto>builder()
                        .message(AppMessages.NOT_FOUND)
                        .success(false)
                        .code(AppCode.NOT_FOUND)
                        .build();
            }
            EmployeeDto employeeDto = employeeMapper.toDto(optional.get());

            return ResponseDto.<EmployeeDto>builder()
                    .message(AppMessages.OK)
                    .success(true)
                    .code(AppCode.OK)
                    .data(employeeDto)
                    .build();
        }catch (DataAccessException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseDto.<EmployeeDto>builder()
                    .message(e.getMessage())
                    .code(AppCode.ERROR)
                    .success(false)
                    .build();
        }
    }


    @Override
    public ResponseDto<String> deleteById(Integer id) {
        try {
            employeeRepository.deleteById(id);
            return ResponseDto.<String>builder()
                    .message(AppMessages.OK)
                    .code(AppCode.OK)
                    .success(false)
                    .build();
        }catch (DataAccessException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseDto.<String>builder()
                    .message(e.getMessage())
                    .success(false)
                    .code(AppCode.ERROR)
                    .build();
        }
    }


    @Override
    public ResponseDto<String> update(EmployeeDto employeeDto) {
        try {
            List<ValidatorDto> errors = validatorService.validateEmployee(employeeDto);
            if (!errors.isEmpty()){
                return ResponseDto.<String>builder()
                        .code(VALIDATOR_ERROR)
                        .errors(errors)
                        .message(VALIDATOR_MESSAGE)
                        .success(false)
                        .build();
            }
            Integer id = Math.toIntExact(SecurityUtil.getEmployeeDto().getId());
            Employee employee = employeeRepository.findById(id).get();

//            if (employeeDto.getUsername() != null) {
//                String username = "admin_" + employeeDto.getUsername();
//                if (employeeRepository.existsByUsername(username)) {
//                    return ResponseDto.<String>builder()
//                            .code(AppCode.DATABASE_ERROR)
//                            .success(false)
//                            .message("Error: Username is already taken!")
//                            .build();
//                }
//                employee.setUsername(username);
//            }
//            if (employeeDto.getPassword() != null) {
//                String password = passwordEncoder.encode(employeeDto.getPassword());
//                employee.setPassword(password);
//                return null;
//            }

            employee.setFirstName(employeeDto.getFirstName() == null ? employee.getFirstName() : employeeDto.getFirstName());
            employee.setLastName(employeeDto.getLastName() == null ? employee.getLastName() : employeeDto.getLastName());
            employee.setPhoneNum1(employeeDto.getPhoneNum1() == null ? employee.getPhoneNum1() : employeeDto.getPhoneNum1());
            employee.setPhoneNum2(employeeDto.getPhoneNum2() == null ? employee.getPhoneNum2() : employeeDto.getPhoneNum2());
            employee.setAddress(employeeDto.getAddress() == null ? employee.getAddress() : employeeDto.getAddress());
            employee.setActive(employeeDto.getActive() == null ? employee.getActive() : employeeDto.getActive());
            employee.setWorkplace(employeeDto.getWorkplace() == null ? employee.getWorkplace() : employeeDto.getWorkplace());
            employee.setPassportNo(employeeDto.getPassportNo() == null ? employee.getPassportNo() : employeeDto.getPassportNo());

            if (employeeDto.getBirthDate() != null) {
                LocalDateTime birth = LocalDateTime.parse(employeeDto.getBirthDate());
                employee.setBirthDate(birth);
            }

            employeeRepository.save(employee);

            return ResponseDto.<String>builder()
                    .message(AppMessages.SAVED)
                    .code(AppCode.OK)
                    .success(true)
                    .build();
        }catch (NullPointerException | IllegalArgumentException | DataAccessException | NoSuchElementException e) {
            log.error(e.getMessage());
            return ResponseDto.<String>builder()
                    .message(e.getMessage())
                    .code(AppCode.ERROR)
                    .success(false)
                    .build();
        }
    }


    @Override
    public ResponseDto<String> setIsActive(Integer id, Boolean active) {
        try {
            Employee employee = employeeRepository.findById(id).get();
            employee.setActive(active);
            employeeRepository.save(employee);

            return ResponseDto.<String>builder()
                    .message(AppMessages.SAVED)
                    .code(AppCode.OK)
                    .success(true)
                    .data(employee.getActive() ? "True" : "False")
                    .build();
        }catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseDto.<String>builder()
                    .message(e.getMessage())
                    .code(AppCode.ERROR)
                    .success(false)
                    .build();
        }
    }


    @Override
    public ResponseDto<String> setRoles(Set<String> setRoles, Integer id) {
        try {
            Employee employee = employeeRepository.findById(id).get();
            Set<Role> roles = new HashSet<>();

            if (setRoles == null) {
                return ResponseDto.<String>builder()
                        .code(AppCode.VALIDATOR_ERROR)
                        .success(false)
                        .message("role " + AppMessages.EMPTY_FIELD)
                        .build();
            } else {
                setRoles.forEach(role -> {
                    switch (role) {
                        case "admin" -> {
                            Role adminRole = roleRepository.findByName(SecurityUtil.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(adminRole);
                        }
                        case "mod" -> {
                            Role modRole = roleRepository.findByName(SecurityUtil.MODERATOR)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(modRole);
                        }
                        case "courier" -> {
                            Role courierRole = roleRepository.findByName(SecurityUtil.ROLE_COURIER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(courierRole);
                        }
                        case "employee" -> {
                            Role employeeRole = roleRepository.findByName(SecurityUtil.ROLE_EMPLOYEE)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(employeeRole);
                        }
                        default -> {
                            Role userRole = roleRepository.findByName(SecurityUtil.ROLE_USER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        }
                    }
                });
            }

            employee.setRoles(roles);
            employeeRepository.save(employee);

            return ResponseDto.<String>builder()
                    .message(AppMessages.SAVED)
                    .code(AppCode.OK)
                    .success(true)
                    .build();
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseDto.<String>builder()
                    .message(e.getMessage())
                    .code(AppCode.ERROR)
                    .success(false)
                    .build();
        }
    }


    @Override
    public ResponseEntity<?> uploadImage(MultipartFile multipartFile) {
        Integer id = Math.toIntExact(SecurityUtil.getEmployeeDto().getId());
        Employee employee = employeeRepository.findById(id).get();

        Long imageId = employee.getImageId();

        ImageDto image = (ImageDto) imageService
                .addImage(multipartFile, "employee image").getBody();

        assert image != null;
        employee.setImagePath(image.getImagePath());
        employee.setImageId(image.getId());

        employeeRepository.save(employee);

        if (imageId != null) {
            boolean check = (boolean) imageService.deleteImage(imageId).getBody();
            if (!check) {
                return ResponseEntity.internalServerError().body("Old image not deleted");
            }
        }

        return ResponseEntity.accepted().body(image);
    }


}