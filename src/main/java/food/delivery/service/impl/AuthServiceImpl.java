package food.delivery.service.impl;

import food.delivery.dto.EmployeeDto;
import food.delivery.dto.EmployeeRole;
import food.delivery.dto.LoginDto;
import food.delivery.dto.UserDto;
import food.delivery.dto.template.UserDetailsDto;
import food.delivery.dto.response.JwtResponse;
import food.delivery.dto.response.ResponseDto;
import food.delivery.helper.StringHelper;
import food.delivery.model.Employee;
import food.delivery.model.Role;
import food.delivery.model.User;
import food.delivery.repository.EmployeeRepository;
import food.delivery.repository.RoleRepository;
import food.delivery.repository.UserRepository;
import food.delivery.security.JwtUtil;
import food.delivery.security.SecurityUtil;
import food.delivery.service.AuthService;
import food.delivery.service.mapper.interfaces.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final EmployeeRepository employeeRepository;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmployeeMapper employeeMapper;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private static final Random random = new Random();


    @Override
    public ResponseEntity<?> superAdmin(EmployeeDto employeeDto) {

        String phone = employeeDto.getPhoneNum1();
        if (employeeRepository.existsByPhoneNum1(phone)) {
            return ResponseEntity.internalServerError().body("Raqam allaqachon mavjud!");
        }

        Employee employee = employeeMapper.toEntity(employeeDto);
        if (employee.getActive() == null) {
            employee.setActive(false);
        }
        String salt = StringHelper.generateSalt(15);
        Set<Role> roleSet = new HashSet<>();
        Role moderator = roleRepository.findByName(SecurityUtil.MODERATOR)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roleSet.add(moderator);

        Role admin = roleRepository.findByName(SecurityUtil.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roleSet.add(admin);

        employee.setRoles(roleSet);
        employee.setCreatedAt(LocalDateTime.now());
        employee.setSalt(salt);
        employee.setUsername("employee_"+employee.getPhoneNum1()+"_"+salt);
        employee.setPassword(passwordEncoder.encode(salt + employee.getPassword()));

        employeeRepository.save(employee);
        employeeDto = employeeMapper.toDto(employee);

        return ResponseEntity.ok().body(employeeDto);
    }


    @Override
    public ResponseEntity<?> refreshTokenEmployee() {

        Integer id = Math.toIntExact(SecurityUtil.getEmployeeDto().getId());
        Employee employee = employeeRepository.getReferenceById(id);
        EmployeeDto employeeDto = employeeMapper.toDto(employee);
        employeeDto.setPassword(employee.getCode());

        return ResponseEntity.ok().body(loginEmployee(employeeDto).getBody());
    }


    @Override
    public ResponseEntity<?> checkPhoneNumber(String phone) {

        Map<String, String> map = new HashMap<>();

        if (employeeRepository.existsByPhoneNum1(phone)) {
            map.put("Active", "true");
        } else {
            map.put("Active", "false");
        }

        String code = generateCode();
        map.put(phone, code);

        return ResponseEntity.ok().body(map);
    }


    public ResponseEntity<?> registerEmployee(EmployeeDto employeeDto) {

        String phone = employeeDto.getPhoneNum1();
        if (employeeRepository.existsByPhoneNum1(phone)) {
            return ResponseEntity.internalServerError().body("Raqam allaqachon mavjud!");
        }

        Employee employee = employeeMapper.toEntity(employeeDto);
        if (employee.getActive() == null) {
            employee.setActive(false);
        }
        String salt = StringHelper.generateSalt(15);
        Set<Role> roleSet = new HashSet<>();
        Role newEmployee = roleRepository.findByName(SecurityUtil.NEW_EMPLOYEE)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roleSet.add(newEmployee);

        employee.setRoles(roleSet);
        employee.setCreatedAt(LocalDateTime.now());
        employee.setSalt(salt);
        employee.setUsername("employee_"+employee.getPhoneNum1()+"_"+salt);
        employee.setPassword(passwordEncoder.encode(salt + employee.getPassword()));

        employeeRepository.save(employee);
        employeeDto = employeeMapper.toDto(employee);

        return ResponseEntity.ok().body(employeeDto);
    }


    @Override
    public ResponseEntity<?> roleForEmployee(EmployeeRole employeeRole) {

        Integer id = employeeRole.getEmployeeId();
        List<String> roles = employeeRole.getRoles();
        Employee employee = employeeRepository.findById(id).get();
        Set<Role> roleSet = new HashSet<>();
        Map<Integer, List<String>> map = new HashMap<>();
        List<String> list = new ArrayList<>();

        for (String s : roles) {
            switch (s) {
                case "admin" -> {
                    Role adminRole = roleRepository.findByName(SecurityUtil.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roleSet.add(adminRole);
                    list.add("admin");
                }
                case "mod" -> {
                    Role modRole = roleRepository.findByName(SecurityUtil.MODERATOR)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roleSet.add(modRole);
                    list.add("mod");
                }
                case "employee" -> {
                    Role courierRole = roleRepository.findByName(SecurityUtil.ROLE_EMPLOYEE)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roleSet.add(courierRole);
                    list.add("employee");
                }
            }
        }
        map.put(id, list);

        employee.setRoles(roleSet);
        employeeRepository.save(employee);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(map);
    }


    @Override
    public ResponseEntity<?> loginEmployee(EmployeeDto employeeDto) {

        String phone = employeeDto.getPhoneNum1();
        Optional<Employee> optional = employeeRepository.findByPhoneNum1(phone);
        if (optional.isEmpty()) {
            return ResponseEntity.internalServerError().body("Not found");
        }
        Employee employee = optional.get();
        String salt = employee.getSalt();

        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("employee_" + phone + "_" + salt);
        loginDto.setPassword(salt + employeeDto.getPassword());
        JwtResponse response = getTokenEmployee(loginDto).getData();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }


    @Override
    public ResponseDto<JwtResponse> getTokenEmployee(LoginDto loginDto) {

        String username = loginDto.getUsername();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateJwtToken(authentication);

        UserDetailsDto userDetails = (UserDetailsDto) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setUsername(userDetails.getUsername().substring(9));
        jwtResponse.setToken(token);
        jwtResponse.setId(userDetails.getId());
        jwtResponse.setRoles(roles);

        return ResponseDto.<JwtResponse>builder()
                .code(0)
                .success(true)
                .data(jwtResponse)
                .build();
    }


    @Override
    public ResponseEntity<?> createUserAccount(UserDto userDto) {
        String phone = userDto.getPhoneNum1();
        Long tgId = userDto.getTgId();

        boolean isValid = StringHelper.isValidPhoneNumber(phone);
        if (!isValid) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid phone number");
        }

        String code = generateCode();
        String salt = StringHelper.generateSalt(15);

        if (code.isEmpty()) { // TODO: send code
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error code sending");
        }

        User user = new User();

        if (userRepository.existsByPhoneNum1(phone)) {
            user = userRepository.findByPhoneNum1(phone).get();
            user.setUsername("user_" + salt + "_" + phone);
            user.setToolWord(code);
            user.setPassword(passwordEncoder.encode(salt + code));
            user.setSalt(salt);
        } else if (userRepository.existsByTgId(tgId)) {
            user.setPhoneNum1(phone);
            user.setToolWord(code);
            user.setSalt(salt);
            user.setUsername("user_" + salt + "_" + phone);
            user.setPassword(passwordEncoder.encode(salt + code));
        } else {
            Set<Role> roles = new HashSet<>();
            Role role = roleRepository.findByName("ROLE_USER").get();
            roles.add(role);

            user.setCreatedAt(LocalDateTime.now());
            user.setIsActive(false);
            user.setPhoneNum1(phone);
            user.setToolWord(code);
            user.setRoles(roles);
            user.setSalt(salt);
            user.setUsername("user_" + salt + "_" + phone);
            user.setPassword(passwordEncoder.encode(salt + code));
        }
        userRepository.save(user);
        Map<String, String> map = new HashMap<>();
        map.put("code", code);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(map);
    }


    @Override
    public ResponseEntity<?> loginUserCheckCode(UserDto userDto) {
        String phone = userDto.getPhoneNum1();
        String code = userDto.getCode();

        User user = userRepository.findByPhoneNum1(phone).get();
        String temp = user.getToolWord();
        String salt = user.getSalt();

        if (!code.equals(temp)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid code");
        }

        LoginDto loginDto = new LoginDto();
        loginDto.setUsername(salt + "_" + phone);
        loginDto.setPassword(salt + code);
        String token = loginUser(loginDto).getData().getToken();

        userRepository.save(user);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(map);
    }


    @Override
    public ResponseDto<JwtResponse> loginUser(LoginDto loginDto) {

        String username = "user_" + loginDto.getUsername();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateJwtToken(authentication);

        UserDetailsDto userDetails = (UserDetailsDto) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setUsername(userDetails.getUsername().substring(5));
        jwtResponse.setToken(token);
        jwtResponse.setId(userDetails.getId());
        jwtResponse.setRoles(roles);

        return ResponseDto.<JwtResponse>builder()
                .data(jwtResponse)
                .code(0)
                .build();
    }



    public String generateCode() {
        Integer code = random.nextInt(10000, 99999);
        return String.valueOf(code);
    }


//    @Override
//    public ResponseEntity<?> createEmployeeAccount(EmployeeDto employeeDto) {
//        String phone = employeeDto.getPhoneNum1();
//        boolean isValid = StringHelper.isValidPhoneNumber(phone);
//        if (!isValid) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid phone number");
//        }
//
//        String code = generateCode();
//        String salt = StringHelper.generateSalt(15);
//
//        if (code.isEmpty()) { // TODO: send code
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error code sending");
//        }
//
//        Employee employee = new Employee();
//
//        if (employeeRepository.existsByPhoneNum1(phone)) {
//            employee = employeeRepository.findByPhoneNum1(phone).get();
////            employee.setToolWord(code);
//            employee.setPassword(passwordEncoder.encode(salt + code));
//            employee.setUsername("employee_" + salt + "_" + phone);
//            employee.setSalt(salt);
//        } else {
//            Set<Role> roles = new HashSet<>();
//            Role role = roleRepository.findByName("ROLE_EMPLOYEE").get();
//            roles.add(role);
//
//            employee.setPhoneNum1(phone);
////            employee.setToolWord(code);
//            employee.setRoles(roles);
//            employee.setSalt(salt);
//            employee.setUsername("employee_" + salt + "_" + phone);
//            employee.setPassword(passwordEncoder.encode(salt + code));
//        }
//        employeeRepository.save(employee);
//        Map<String, String> map = new HashMap<>();
//        map.put("code", code);
//
//        return ResponseEntity.status(HttpStatus.ACCEPTED).body(map);
//    }


    @PostConstruct
    public void superAdmin() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setPhoneNum1("+998909345670");
        employeeDto.setPassword("abu_1234");
        superAdmin(employeeDto);
    }
}
