package food.delivery.service.mapper.interfaces;

import food.delivery.dto.EmployeeDto;
import food.delivery.dto.EmployeeDto.EmployeeDtoBuilder;
import food.delivery.model.Employee;
import food.delivery.model.Employee.EmployeeBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-22T00:00:54+0500",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public Employee toEntity(EmployeeDto employeeDto) {
        if ( employeeDto == null ) {
            return null;
        }

        EmployeeBuilder employee = Employee.builder();

        if ( employeeDto.getBirthDate() != null ) {
            employee.birthDate( LocalDate.parse( employeeDto.getBirthDate(), DateTimeFormatter.ofPattern( "yyyy-MM-dd" ) ) );
        }
        if ( employeeDto.getCreatedAt() != null ) {
            employee.createdAt( LocalDateTime.parse( employeeDto.getCreatedAt(), DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm" ) ) );
        }
        employee.id( employeeDto.getId() );
        employee.firstName( employeeDto.getFirstName() );
        employee.lastName( employeeDto.getLastName() );
        employee.password( employeeDto.getPassword() );
        employee.phoneNum1( employeeDto.getPhoneNum1() );
        employee.phoneNum2( employeeDto.getPhoneNum2() );
        employee.address( employeeDto.getAddress() );
        employee.gender( employeeDto.getGender() );
        employee.active( employeeDto.getActive() );
        employee.workplace( employeeDto.getWorkplace() );
        employee.salary( employeeDto.getSalary() );
        employee.passportNo( employeeDto.getPassportNo() );
        employee.imagePath( employeeDto.getImagePath() );

        return employee.build();
    }

    @Override
    public EmployeeDto toDto(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeDtoBuilder employeeDto = EmployeeDto.builder();

        if ( employee.getBirthDate() != null ) {
            employeeDto.birthDate( DateTimeFormatter.ofPattern( "yyyy-MM-dd" ).format( employee.getBirthDate() ) );
        }
        if ( employee.getCreatedAt() != null ) {
            employeeDto.createdAt( DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm" ).format( employee.getCreatedAt() ) );
        }
        employeeDto.id( employee.getId() );
        employeeDto.firstName( employee.getFirstName() );
        employeeDto.lastName( employee.getLastName() );
        employeeDto.phoneNum1( employee.getPhoneNum1() );
        employeeDto.phoneNum2( employee.getPhoneNum2() );
        employeeDto.address( employee.getAddress() );
        employeeDto.active( employee.getActive() );
        employeeDto.gender( employee.getGender() );
        employeeDto.workplace( employee.getWorkplace() );
        employeeDto.salary( employee.getSalary() );
        employeeDto.passportNo( employee.getPassportNo() );
        employeeDto.imagePath( employee.getImagePath() );

        return employeeDto.build();
    }
}
