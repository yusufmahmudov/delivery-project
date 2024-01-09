package food.delivery.security;

import food.delivery.dto.template.UserDetailsDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static final String MODERATOR = "ROLE_MODERATOR";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_EMPLOYEE = "ROLE_EMPLOYEE";
    public static final String NEW_EMPLOYEE = "NEW_EMPLOYEE";

    public static UserDetailsDto getEmployeeDto() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsDto) authentication.getPrincipal();
    }

    public static UserDetailsDto getUserDto() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsDto) authentication.getPrincipal();
    }

}
