package food.delivery.service;

import food.delivery.dto.EmployeeRole;
import food.delivery.dto.LoginDto;
import food.delivery.dto.response.JwtResponse;
import food.delivery.dto.response.ResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface AuthService {

    ResponseEntity<?> roleForEmployee(EmployeeRole employeeRole);

    ResponseEntity<?> createEmployeeAccount(String phone);

    ResponseEntity<?> loginEmployeeCheckCode(String phone, String code);

    ResponseDto<JwtResponse> loginEmployee(LoginDto loginDto);



    ResponseEntity<?> createUserAccount(String phone, Long tgId);

    ResponseEntity<?> loginUserCheckCode(String phone, String code);

    ResponseDto<JwtResponse> loginUser(LoginDto loginDto);

}
