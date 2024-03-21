package food.delivery.service.impl;

import food.delivery.dto.*;
import food.delivery.dto.response.GetResponse;
import food.delivery.dto.response.ValidatorDto;
import food.delivery.dto.template.ImageDto;
import food.delivery.helper.AppMessages;
import food.delivery.helper.StringHelper;
import food.delivery.model.Employee;
import food.delivery.model.Role;
import food.delivery.repository.EmployeeRepository;
import food.delivery.repository.RoleRepository;
import food.delivery.security.SecurityUtil;
import food.delivery.service.EmployeeService;
import food.delivery.service.ImageService;
import food.delivery.service.ValidatorService;
import food.delivery.service.mapper.interfaces.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

import static food.delivery.helper.AppMessages.VALIDATOR_MESSAGE;


@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ValidatorService validatorService;

    private final EmployeeMapper employeeMapper;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final ImageService imageService;
    @Value("${main.domain}")
    private String domain;


    @Override
    public ResponseEntity<?> allEmployee(Integer limit, Integer offset) {
        try {
            List<EmployeeDto> employeeDtos = employeeRepository.findAll()
                    .stream().map(employeeMapper::toDto).toList();
            List<EmployeeDto> result = new ArrayList<>();

            GetResponse response = new GetResponse();
            response.setCount(0);
            response.setPrevious(domain + "/employee/all/?limit="
                    +limit+"&offset=0");
            response.setData(result);

            if (employeeDtos.size() <= offset) {
                return ResponseEntity.ok().body(response);
            }

            for (int i = offset; i < offset+limit; i++) {
                result.add(employeeDtos.get(i));
                if (employeeDtos.size()-1 == i) break;
            }

            response.setCount(result.size());
            response.setData(result);
            response.setNext(employeeDtos.size() >= offset+limit?domain + "/employee/all/?limit="+limit
                    +"&offset="+(offset+limit):null);
            response.setPrevious(domain + "/employee/all/?limit="+limit+"&offset=" + (Math.max(offset-limit, 0)));

            return ResponseEntity.ok().body(response);
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> allEmployeeIsActive(Boolean active, Integer limit, Integer offset) {
        try {
            List<EmployeeDto> employeeDtos = employeeRepository.findAllByActive(active)
                    .stream().map(employeeMapper::toDto).toList();
            List<EmployeeDto> result = new ArrayList<>();

            GetResponse response = new GetResponse();
            response.setCount(0);
            response.setPrevious(domain + "/employee/all-active/?active"+active+"&limit="
                    +limit+"&offset=0");
            response.setData(result);

            if (employeeDtos.size() <= offset) {
                return ResponseEntity.ok().body(response);
            }

            for (int i = offset; i < offset+limit; i++) {
                result.add(employeeDtos.get(i));
                if (employeeDtos.size()-1 == i) break;
            }

            response.setCount(result.size());
            response.setData(result);
            response.setNext(employeeDtos.size() >= offset+limit?domain + "/employee/all-active/?" +
                    "active="+active+"&limit="+limit+"&offset="+(offset+limit):null);
            response.setPrevious(domain + "/employee/all-active/?active="
                    +active+"&limit="+limit+"&offset=" + (Math.max(offset-limit, 0)));

            return ResponseEntity.ok().body(response);
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> getByActiveTrueAndWorkplace(
            String workplace, Boolean active, Integer limit, Integer offset){
        try {
            List<EmployeeDto> employeeDtos = employeeRepository.findAllByActiveAndWorkplace(active, workplace)
                    .stream().map(employeeMapper::toDto).toList();
            List<EmployeeDto> result = new ArrayList<>();

            GetResponse response = new GetResponse();
            response.setCount(0);
            response.setPrevious(domain + "/employee/all-workplace/?workplace="
                    +workplace+"&active"+active+"&limit="+limit+"&offset=0");
            response.setData(result);

            if (employeeDtos.size() <= offset) {
                return ResponseEntity.ok().body(response);
            }

            for (int i = offset; i < offset+limit; i++) {
                result.add(employeeDtos.get(i));
                if (employeeDtos.size()-1 == i) break;
            }

            response.setCount(result.size());
            response.setData(result);
            response.setNext(employeeDtos.size() >= offset+limit?domain + "/employee/all-workplace/?" +
                    "workplace="+workplace+"&active="+active+"&limit="+limit+"&offset="+(offset+limit):null);
            response.setPrevious(domain + "/employee/all-workplace/?workplace="+workplace+"&active="
                    +active+"&limit="+limit+"&offset=" + (Math.max(offset-limit, 0)));

            return ResponseEntity.ok().body(response);
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> getById() {
        try {
            Integer id = Math.toIntExact(SecurityUtil.getEmployeeDto().getId());
            Optional<Employee> optional = employeeRepository.findById(id);
            if (optional.isEmpty()){
                return ResponseEntity.internalServerError().body(AppMessages.NOT_FOUND);
            }
            EmployeeDto employeeDto = employeeMapper.toDto(optional.get());

            return ResponseEntity.ok().body(employeeDto);
        }catch (DataAccessException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> deleteById(Integer id) {
        try {
            employeeRepository.deleteById(id);
            return ResponseEntity.ok().body("Deleted!");
        }catch (DataAccessException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> update(EmployeeDto employeeDto) {
        try {
            List<ValidatorDto> errors = validatorService.validateEmployee(employeeDto);
            if (!errors.isEmpty()){
                return ResponseEntity.internalServerError().body(VALIDATOR_MESSAGE);
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
//                employee.setBirthDate(birth);
            }

            employeeRepository.save(employee);

            return ResponseEntity.ok().body(employee);
        }catch (NullPointerException | IllegalArgumentException | DataAccessException | NoSuchElementException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> updatePassword(String password) {
        try {
            Integer id = Math.toIntExact(SecurityUtil.getEmployeeDto().getId());
            Employee employee = employeeRepository.findById(id).get();
            String salt = StringHelper.generateSalt(15);

            String pass = passwordEncoder.encode(salt + employee.getPassword());

            employee.setSalt(salt);
            employee.setPassword(pass);

            return ResponseEntity.ok().body(employeeMapper.toDto(employee));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> setIsActive(Integer id, Boolean active) {
        try {
            Employee employee = employeeRepository.findById(id).get();
            employee.setActive(active);
            employeeRepository.save(employee);

            return ResponseEntity.ok().body(employee.getActive() ? "True" : "False");
        }catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


//    @Override
//    public ResponseEntity<?> setRoles(Set<String> setRoles, Integer id) {
//        try {
//            Employee employee = employeeRepository.findById(id).get();
//            Set<Role> roles = new HashSet<>();
//
//            if (setRoles == null) {
//                return ResponseEntity.internalServerError().body("role " + AppMessages.EMPTY_FIELD);
//            } else {
//                setRoles.forEach(role -> {
//                    switch (role) {
//                        case "admin" -> {
//                            Role adminRole = roleRepository.findByName(SecurityUtil.ROLE_ADMIN)
//                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                            roles.add(adminRole);
//                        }
//                        case "mod" -> {
//                            Role modRole = roleRepository.findByName(SecurityUtil.MODERATOR)
//                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                            roles.add(modRole);
//                        }
//                        case "courier" -> {
//                            Role courierRole = roleRepository.findByName(SecurityUtil.ROLE_COURIER)
//                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                            roles.add(courierRole);
//                        }
//                        case "employee" -> {
//                            Role employeeRole = roleRepository.findByName(SecurityUtil.ROLE_EMPLOYEE)
//                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                            roles.add(employeeRole);
//                        }
//                        default -> {
//                            Role userRole = roleRepository.findByName(SecurityUtil.ROLE_USER)
//                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        }
//                    }
//                });
//            }
//
//            employee.setRoles(roles);
//            employeeRepository.save(employee);
//
//            return ResponseEntity.ok().body(employee.getRoles());
//        }catch (RuntimeException e) {
//            log.error(e.getMessage());
//            return ResponseEntity.internalServerError().body(e.getMessage());
//        }
//    }


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