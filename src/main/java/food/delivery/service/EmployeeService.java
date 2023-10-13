package food.delivery.service;


import food.delivery.dto.EmployeeDto;
import food.delivery.dto.response.ResponseDto;
import jdk.jfr.consumer.RecordedEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface EmployeeService {

    ResponseEntity<?> allEmployee(Integer limit, Integer offset);

    ResponseEntity<?> allEmployeeIsActive(Boolean active, Integer limit, Integer offset);

    ResponseEntity<?> getByActiveTrueAndWorkplace(String workplace, Boolean active, Integer limit, Integer offset);

    ResponseEntity<?> getById();

    ResponseEntity<?> deleteById(Integer id);

    ResponseEntity<?> update(EmployeeDto employeeDto);

    ResponseEntity<?> setIsActive(Integer id, Boolean active);

    ResponseEntity<?> setRoles(Set<String> roles, Integer id);

    ResponseEntity<?> uploadImage(MultipartFile multipartFile);
}
