package food.delivery.service.mapper.interfaces;

import food.delivery.dto.UserDto;
import food.delivery.dto.UserDto.UserDtoBuilder;
import food.delivery.model.User;
import food.delivery.model.User.UserBuilder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-03T22:34:36+0500",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        UserBuilder user = User.builder();

        if ( userDto.getCreatedAt() != null ) {
            user.createdAt( LocalDateTime.parse( userDto.getCreatedAt(), DateTimeFormatter.ofPattern( "dd.MM.yyyy HH:mm" ) ) );
        }
        user.id( userDto.getId() );
        user.fullName( userDto.getFullName() );
        user.gender( userDto.getGender() );
        if ( userDto.getBirthDate() != null ) {
            user.birthDate( LocalDateTime.parse( userDto.getBirthDate() ) );
        }
        user.phoneNum1( userDto.getPhoneNum1() );
        user.phoneNum2( userDto.getPhoneNum2() );
        user.tgUsername( userDto.getTgUsername() );
        user.tgId( userDto.getTgId() );
        user.languageCode( userDto.getLanguageCode() );
        user.isAdmin( userDto.getIsAdmin() );

        return user.build();
    }

    @Override
    public UserDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDtoBuilder userDto = UserDto.builder();

        if ( user.getCreatedAt() != null ) {
            userDto.createdAt( DateTimeFormatter.ofPattern( "dd.MM.yyyy HH:mm" ).format( user.getCreatedAt() ) );
        }
        userDto.id( user.getId() );
        userDto.fullName( user.getFullName() );
        userDto.phoneNum1( user.getPhoneNum1() );
        userDto.phoneNum2( user.getPhoneNum2() );
        userDto.gender( user.getGender() );
        if ( user.getBirthDate() != null ) {
            userDto.birthDate( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( user.getBirthDate() ) );
        }
        userDto.tgUsername( user.getTgUsername() );
        userDto.tgId( user.getTgId() );
        userDto.languageCode( user.getLanguageCode() );
        userDto.isAdmin( user.getIsAdmin() );

        return userDto.build();
    }
}
