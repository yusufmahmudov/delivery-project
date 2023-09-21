package food.delivery.config;

import food.delivery.security.JwtEntryPoint;
import food.delivery.security.JwtFilter;
import food.delivery.service.impl.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalAuthentication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@SecurityScheme(type = SecuritySchemeType.APIKEY, name = "Authorization", in = SecuritySchemeIn.HEADER)
@OpenAPIDefinition(info = @Info(title = "Delivery service",
        description = "Food delivery Projects",
        termsOfService = "url",
        contact = @Contact(name = "Yusuf Mahmudov",
                email = "yusufmahmudov909@gmail.com",
                url = "https://t.me/yusufmahmudov"
        ),
        version = "1.0.0",
        license = @License(name = "MIT", url = "https://opensource.org/licenses/MIT")
),
        security = @SecurityRequirement(name = "Authorization")
)
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private JwtEntryPoint jwtEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/swagger-ui/**",
                        "/v3/api-docs/**"
                ).permitAll()
//                .antMatchers("/category/**").permitAll()
//                .antMatchers("/product/**").permitAll()
//                .antMatchers("/location/**").permitAll()
//                .antMatchers("/image/**").permitAll()
//                .antMatchers("/employee/**").permitAll()
//                .antMatchers("/user/**").permitAll()
                .anyRequest().authenticated();

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}
