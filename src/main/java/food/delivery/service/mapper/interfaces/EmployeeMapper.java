package food.delivery.service.mapper.interfaces;

import food.delivery.dto.EmployeeDto;
import food.delivery.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "birthDate", source = "birthDate", dateFormat = "dd.MM.yyyy")
    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "dd.MM.yyyy HH:mm")
    Employee toEntity(EmployeeDto employeeDto);

    @Mapping(target = "birthDate", source = "birthDate", dateFormat = "dd.MM.yyyy")
    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "dd.MM.yyyy HH:mm")
    EmployeeDto toDto(Employee employee);

}
