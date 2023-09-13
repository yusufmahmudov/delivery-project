package food.delivery.service;


import food.delivery.dto.EmployeeDto;
import food.delivery.dto.response.ResponseDto;
import jdk.jfr.consumer.RecordedEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface EmployeeService {

    ResponseDto<List<EmployeeDto>> allEmployee();

    ResponseDto<List<EmployeeDto>> allEmployeeIsActive(Boolean active);

    ResponseDto<List<EmployeeDto>> getByActiveTrueAndWorkplace(String workplace, Boolean active);

    ResponseDto<EmployeeDto> getById();

    ResponseDto<String> deleteById(Integer id);

    ResponseDto<String> update(EmployeeDto employeeDto);

    ResponseDto<String> setIsActive(Integer id, Boolean active);

    ResponseDto<String> setRoles(Set<String> roles, Integer id);

    ResponseEntity<?> uploadImage(MultipartFile multipartFile);
}
