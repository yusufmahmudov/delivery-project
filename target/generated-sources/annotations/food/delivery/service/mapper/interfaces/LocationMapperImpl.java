package food.delivery.service.mapper.interfaces;

import food.delivery.dto.LocationDto;
import food.delivery.dto.LocationDto.LocationDtoBuilder;
import food.delivery.model.Location;
import food.delivery.model.Location.LocationBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-03T23:21:44+0500",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class LocationMapperImpl implements LocationMapper {

    @Override
    public Location toEntity(LocationDto locationDto) {
        if ( locationDto == null ) {
            return null;
        }

        LocationBuilder location = Location.builder();

        location.id( locationDto.getId() );
        location.address( locationDto.getAddress() );
        location.latitude( locationDto.getLatitude() );
        location.longitude( locationDto.getLongitude() );
        location.userId( locationDto.getUserId() );

        return location.build();
    }

    @Override
    public LocationDto toDto(Location location) {
        if ( location == null ) {
            return null;
        }

        LocationDtoBuilder locationDto = LocationDto.builder();

        locationDto.id( location.getId() );
        locationDto.address( location.getAddress() );
        locationDto.latitude( location.getLatitude() );
        locationDto.longitude( location.getLongitude() );
        locationDto.userId( location.getUserId() );

        return locationDto.build();
    }
}
