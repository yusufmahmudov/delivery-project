package food.delivery.service;


import food.delivery.dto.*;
import food.delivery.dto.response.ValidatorDto;

import java.util.List;

public interface ValidatorService {

    List<ValidatorDto> validateProduct(ProductDto productDto);

    List<ValidatorDto> validateFilial(FilialDto filialDto);

    List<ValidatorDto> validateUser(UserDto userDto);

    List<ValidatorDto> validateEmployee(EmployeeDto employeeDto);

}
