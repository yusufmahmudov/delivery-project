//package food.delivery.service.mapper;
//
//
//import food.delivery.dto.EmployeeDto;
//import food.delivery.model.Employee;
//
//public class EmployeeMapper {
//
//    public static EmployeeDto toDto(Employee employee){
//        return EmployeeDto.builder()
//                .id(employee.getId())
//                .firstName(employee.getFirstName())
//                .lastName(employee.getLastName())
//                .username(employee.getUsername())
//                .phoneNum1(employee.getPhoneNum1())
//                .phoneNum2(employee.getPhoneNum2())
//                .address(employee.getAddress())
//                .workplace(employee.getWorkplace())
//                .active(employee.getActive())
//                .birthDate(employee.getBirthDate())
//                .passportNo(employee.getPassportNo())
//                .build();
//    }
//
//    public static Employee toEntity(EmployeeDto employeeDto){
//        return Employee.builder()
//                .id(employeeDto.getId())
//                .firstName(employeeDto.getFirstName())
//                .lastName(employeeDto.getLastName())
//                .username(employeeDto.getUsername())
//                .phoneNum1(employeeDto.getPhoneNum1())
//                .phoneNum2(employeeDto.getPhoneNum2())
//                .address(employeeDto.getAddress())
//                .workplace(employeeDto.getWorkplace())
//                .active(employeeDto.getActive())
//                .password(employeeDto.getPassword())
//                .passportNo(employeeDto.getPassportNo())
//                .birthDate(employeeDto.getBirthDate())
//                .build();
//    }
//
//}
