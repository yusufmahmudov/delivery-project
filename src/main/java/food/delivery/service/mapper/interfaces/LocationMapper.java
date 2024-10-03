package food.delivery.service.mapper.interfaces;

import food.delivery.dto.LocationDto;
import food.delivery.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    Location toEntity(LocationDto locationDto);

    LocationDto toDto(Location location);

}
