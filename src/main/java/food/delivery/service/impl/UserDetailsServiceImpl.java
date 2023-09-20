package food.delivery.service.impl;

import food.delivery.dto.template.UserDetailsDto;
import food.delivery.model.Employee;
import food.delivery.model.User;
import food.delivery.repository.EmployeeRepository;
import food.delivery.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return UserDetailsDto.buildUser(optionalUser.get());
        }
        Optional<Employee> optionalEmployee = employeeRepository.findByUsername(username);

        return optionalEmployee.map(UserDetailsDto::buildEmployee).orElse(null); // employee_3Dr0tEl69jNy15y_+998909098108
    }

}
