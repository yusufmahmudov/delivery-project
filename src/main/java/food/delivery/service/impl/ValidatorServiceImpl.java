package food.delivery.service.impl;

import food.delivery.dto.response.ValidatorDto;
import food.delivery.helper.AppMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import food.delivery.dto.*;
import food.delivery.helper.StringHelper;
import food.delivery.repository.EmployeeRepository;
import food.delivery.service.ValidatorService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ValidatorServiceImpl implements ValidatorService {

    private final EmployeeRepository employeeRepository;

    @Override
    public List<ValidatorDto> validateProduct(ProductDto productDto) {

        List<ValidatorDto> errors = new ArrayList<>();

        if (productDto.getName() != null &&
                !StringHelper.isValidLength(productDto.getName(), 3, 120)) {
            errors.add(new ValidatorDto("name", "length min 3, max 120"));
        }

        if (productDto.getDiscount() != null &&
                (productDto.getDiscount() <= 0.0 || productDto.getDiscount() >= 100.0)) {
            errors.add(new ValidatorDto("discount", "min value = 0.0, max value = 100.0"));
        }

        if (productDto.getPrice() != null && productDto.getPrice() < 0.0) {
            errors.add(new ValidatorDto("price", AppMessages.NEGATIVE_VALUE));
        }

        if (productDto.getDescription() != null && productDto.getDescription().length() > 512) {
            errors.add(new ValidatorDto("description", "length max 512"));
        }

        return errors;
    }


    @Override
    public List<ValidatorDto> validateFilial(FilialDto filialDto) {

        List<ValidatorDto> errors = new ArrayList<>();

        if ((filialDto.getName() != null) &&
                !StringHelper.isValidLength(filialDto.getName(), 4, 255)) {
            errors.add(new ValidatorDto("name", "length min 4 and max 255"));
        }

        if (filialDto.getPhoneNum() != null && !StringHelper.isValidPhoneNumber(filialDto.getPhoneNum())) {
            errors.add(new ValidatorDto("phoneNum", "Invalid input!"));
        }

        if (filialDto.getAdminId() != null && filialDto.getAdminId() < 1) {
            errors.add(new ValidatorDto("adminId", AppMessages.NEGATIVE_VALUE));
        }

        if ((filialDto.getAddress() != null) &&
                !StringHelper.isValidLength(filialDto.getAddress(), 4, 255)) {
            errors.add(new ValidatorDto("address", "length min 4 and max 255"));
        }

        return errors;
    }


    @Override
    public List<ValidatorDto> validateUser(UserDto userDto) {

        List<ValidatorDto> errors = new ArrayList<>();

        if (userDto.getFullName() != null &&
                !StringHelper.isValidLength(userDto.getFullName(), 3, 64)) {
            errors.add(new ValidatorDto("fullName", "length min 3, max 64"));
        }

        if (userDto.getFullName() != null &&
                !StringHelper.isStringContainsLetter(userDto.getFullName())) {
            errors.add(new ValidatorDto("fullName", "Faqat harflardan iborat bo'lishi kerak"));
        }

        if (userDto.getPhoneNum1() != null && !StringHelper.isValidPhoneNumber(userDto.getPhoneNum1())) {
            errors.add(new ValidatorDto("phoneNum1", "Invalid phone"));
        }

        if (userDto.getPhoneNum2() != null && !StringHelper.isValidPhoneNumber(userDto.getPhoneNum2())) {
            errors.add(new ValidatorDto("phoneNum2", "Invalid phone"));
        }

        return errors;
    }


    @Override
    public List<ValidatorDto> validateEmployee(EmployeeDto employeeDto) {

        List<ValidatorDto> errors = new ArrayList<>();

        if (employeeDto.getFirstName() != null &&
                !StringHelper.isValidLength(employeeDto.getFirstName(), 3, 30)) {
            errors.add(new ValidatorDto("First name", "length min 3, max 30"));
        }

        if (employeeDto.getFirstName() != null &&
                !StringHelper.isStringContainsLetter(employeeDto.getFirstName())) {
            errors.add(new ValidatorDto("First name", "Faqat harflardan iborat bo'lishi kerak"));
        }

        if (employeeDto.getLastName() != null &&
                !StringHelper.isValidLength(employeeDto.getLastName(), 3, 30)) {
            errors.add(new ValidatorDto("Last name", "length min 3, max 30"));
        }

        if (employeeDto.getLastName() != null &&
                !StringHelper.isStringContainsLetter(employeeDto.getLastName())) {
            errors.add(new ValidatorDto("last name", "Faqat harflardan iborat bo'lishi kerak"));
        }

        if (employeeDto.getPhoneNum1() != null && !StringHelper.isValidPhoneNumber(employeeDto.getPhoneNum1())) {
            errors.add(new ValidatorDto("phoneNum1", "Invalid phone"));
        }

        if (employeeDto.getPhoneNum2() != null && !StringHelper.isValidPhoneNumber(employeeDto.getPhoneNum2())) {
            errors.add(new ValidatorDto("phoneNum2", "Invalid phone"));
        }

        if (employeeDto.getAddress() != null && !StringHelper.isValidLength(employeeDto.getAddress(), 0, 120)) {
            errors.add(new ValidatorDto("address", "length max 120"));
        }

        if (employeeDto.getWorkplace() != null && !StringHelper.isValidLength(employeeDto.getWorkplace(), 0, 120)) {
            errors.add(new ValidatorDto("workplace", "length max 120"));
        }

        if(employeeDto.getPassportNo() != null && StringHelper.isValidLength(employeeDto.getPassportNo(), 0, 20)){
            errors.add(new ValidatorDto("passportNo", "Invalid input"));
        }

        return errors;
    }


}
