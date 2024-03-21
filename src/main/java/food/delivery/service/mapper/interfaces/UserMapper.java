package food.delivery.service.mapper.interfaces;

import food.delivery.dto.UserDto;
import food.delivery.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "dd.MM.yyyy HH:mm")
    User toEntity(UserDto userDto);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "dd.MM.yyyy HH:mm")
    UserDto toDto(User user);

}
