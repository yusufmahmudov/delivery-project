package food.delivery.service.impl;

import food.delivery.dto.EmployeeDto;
import food.delivery.dto.LoginDto;
import food.delivery.dto.template.UserDetailsDto;
import food.delivery.dto.response.JwtResponse;
import food.delivery.dto.response.ResponseDto;
import food.delivery.helper.AppCode;
import food.delivery.helper.AppMessages;
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

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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


    public ResponseDto<String> registerEmployee(EmployeeDto employeeDto) {

        String username = "employee_";
        if (employeeRepository.existsByUsername(username)) {
            return ResponseDto.<String>builder()
                    .code(AppCode.DATABASE_ERROR)
                    .success(false)
                    .message("Error: Username is already taken!")
                    .build();
        }

        String salt = StringHelper.generateSalt(8);

        Employee employee = employeeMapper.toEntity(employeeDto);
        employee.setUsername(username);
        employee.setPassword(passwordEncoder.encode(employeeDto.getAddress()));
        employee.setSalt(salt);
        if (employee.getActive() == null) {
            employee.setActive(false);
        }

        Set<String> setRoles = employeeDto.getRole();
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
                .code(AppCode.OK)
                .success(true)
                .message(AppMessages.SAVED)
                .build();
    }


    @Override
    public ResponseEntity<?> roleForEmployee(Integer employeeId, List<String> roles) {
        Employee employee = employeeRepository.findById(employeeId).get();
        Set<Role> roleSet = new HashSet<>();

        for (String s : roles) {
            switch (s) {
                case "admin" -> {
                    Role adminRole = roleRepository.findByName(SecurityUtil.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roleSet.add(adminRole);
                }
                case "mod" -> {
                    Role modRole = roleRepository.findByName(SecurityUtil.MODERATOR)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roleSet.add(modRole);
                }
                case "courier" -> {
                    Role courierRole = roleRepository.findByName(SecurityUtil.ROLE_COURIER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roleSet.add(courierRole);
                }
            }
        }

//        roles.forEach(role -> {
//            switch (role) {
//                case "admin" -> {
//                    Role adminRole = roleRepository.findByName(SecurityUtil.ROLE_ADMIN)
//                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                    roleSet.add(adminRole);
//                }
//                case "mod" -> {
//                    Role modRole = roleRepository.findByName(SecurityUtil.MODERATOR)
//                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                    roleSet.add(modRole);
//                }
//                case "courier" -> {
//                    Role courierRole = roleRepository.findByName(SecurityUtil.ROLE_COURIER)
//                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                    roleSet.add(courierRole);
//                }
//                default -> {
//                    Role userRole = roleRepository.findByName(SecurityUtil.ROLE_USER)
//                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                }
//            }
//        });

        employee.setRoles(roleSet);
        employeeRepository.save(employee);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Roles saved!");
    }


    @Override
    public ResponseEntity<?> createEmployeeAccount(String phone) {
        boolean isValid = StringHelper.isValidPhoneNumber(phone);
        if (!isValid) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid phone number");
        }

        String code = generateCode();
        String salt = StringHelper.generateSalt(15);

        if (code.isEmpty()) { // TODO: send code
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error code sending");
        }

        Employee employee = new Employee();

        if (employeeRepository.existsByPhoneNum1(phone)) {
            employee = employeeRepository.findByPhoneNum1(phone).get();
            employee.setToolWord(code);
            employee.setPassword(passwordEncoder.encode(salt + code));
            employee.setUsername("employee_" + salt + "_" + phone);
            employee.setSalt(salt);
        } else {
            Set<Role> roles = new HashSet<>();
            Role role = roleRepository.findByName("ROLE_EMPLOYEE").get();
            roles.add(role);

            employee.setPhoneNum1(phone);
            employee.setToolWord(code);
            employee.setRoles(roles);
            employee.setSalt(salt);
            employee.setUsername("employee_" + salt + "_" + phone);
            employee.setPassword(passwordEncoder.encode(salt + code));
        }
        employeeRepository.save(employee);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Code sending " + code);
    }


    @Override
    public ResponseEntity<?> loginEmployeeCheckCode(String phone, String code) {
        Employee employee = employeeRepository.findByPhoneNum1(phone).get();
        String temp = employee.getToolWord();
        String salt = employee.getSalt();

        if (!code.equals(temp)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid code");
        }

        LoginDto loginDto = new LoginDto();
        loginDto.setUsername(salt + "_" + phone);
        loginDto.setPassword(salt + code);
        String token = loginEmployee(loginDto).getData().getToken();

        employeeRepository.save(employee);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(token);
    }


    @Override
    public ResponseDto<JwtResponse> loginEmployee(LoginDto loginDto) {

        String username = "employee_" + loginDto.getUsername();

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
    public ResponseEntity<?> createUserAccount(String phone, Long tgId) {

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

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Code sending " + code);
    }


    @Override
    public ResponseEntity<?> loginUserCheckCode(String phone, String code) {

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

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(token);
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
        Integer code = random.nextInt(100000, 999999);
        return String.valueOf(code);
    }


}
