package food.delivery.service.mapper.interfaces;

import food.delivery.dto.EmployeeDto;
import food.delivery.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "birthDate", source = "birthDate", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd HH:mm")
    Employee toEntity(EmployeeDto employeeDto);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "birthDate", source = "birthDate", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd HH:mm")
    EmployeeDto toDto(Employee employee);

}
