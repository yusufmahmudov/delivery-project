package food.delivery.dto.template;


import com.fasterxml.jackson.annotation.JsonIgnore;
import food.delivery.model.Employee;
import food.delivery.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Schema(description = "Security uchun maxsus class")
public class UserDetailsDto implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;


    public static UserDetailsDto buildUser(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new UserDetailsDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                authorities);

    }

    public static UserDetailsDto buildEmployee(Employee employee) {
        List<GrantedAuthority> authorities = employee.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new UserDetailsDto(
                employee.getId(),
                employee.getUsername(),
                employee.getPassword(),
                authorities);

    }

    public UserDetailsDto(Integer id, String username, String password,
                          Collection<? extends GrantedAuthority> authorities) {
        this.id = Long.valueOf(id);
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
