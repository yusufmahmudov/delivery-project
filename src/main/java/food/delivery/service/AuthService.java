package food.delivery.service;

import food.delivery.dto.EmployeeDto;
import food.delivery.dto.EmployeeRole;
import food.delivery.dto.LoginDto;
import food.delivery.dto.UserDto;
import food.delivery.dto.response.JwtResponse;
import food.delivery.dto.response.ResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface AuthService {


    ResponseEntity<?> registerEmployee(EmployeeDto employeeDto);

    ResponseEntity<?> roleForEmployee(EmployeeRole employeeRole);

//    ResponseEntity<?> createEmployeeAccount(EmployeeDto employeeDto);

    ResponseEntity<?> loginEmployee(EmployeeDto employeeDto);

    ResponseDto<JwtResponse> getTokenEmployee(LoginDto loginDto);



    ResponseEntity<?> createUserAccount(UserDto userDto);

    ResponseEntity<?> loginUserCheckCode(UserDto userDto);

    ResponseDto<JwtResponse> loginUser(LoginDto loginDto);

}
