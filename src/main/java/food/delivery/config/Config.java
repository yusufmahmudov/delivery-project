package food.delivery.config;

import food.delivery.dto.EmployeeDto;
import food.delivery.model.Employee;
import food.delivery.model.Role;
import food.delivery.repository.RoleRepository;
import food.delivery.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
public class Config {
    

    @Autowired
    private RoleRepository roleRepository;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @PostConstruct
    public void createRoles() {
        log.info("Program started!");
        Role role1 = new Role();
        Role role2 = new Role();
        Role role3 = new Role();
        Role role4 = new Role();
        Role role5 = new Role();

        role1.setName("ROLE_USER");
        role1.setId(1);
        roleRepository.save(role1);

        role2.setName("ROLE_ADMIN");
        role2.setId(2);
        roleRepository.save(role2);

        role3.setName("ROLE_MODERATOR");
        role3.setId(3);
        roleRepository.save(role3);

        role4.setName("ROLE_EMPLOYEE");
        role4.setId(4);
        roleRepository.save(role4);

        role5.setName("NEW_EMPLOYEE");
        role5.setId(5);
        roleRepository.save(role5);

    }


}
