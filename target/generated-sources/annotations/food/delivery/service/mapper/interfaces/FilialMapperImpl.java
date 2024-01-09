package food.delivery.service.mapper.interfaces;

import food.delivery.dto.FilialDto;
import food.delivery.dto.FilialDto.FilialDtoBuilder;
import food.delivery.model.Filial;
import food.delivery.model.Filial.FilialBuilder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-03T22:34:36+0500",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class FilialMapperImpl implements FilialMapper {

    @Override
    public FilialDto toDto(Filial filial) {
        if ( filial == null ) {
            return null;
        }

        FilialDtoBuilder filialDto = FilialDto.builder();

        if ( filial.getOpeningTime() != null ) {
            filialDto.openingTime( DateTimeFormatter.ofPattern( "dd.MM.yyyy HH:mm" ).format( filial.getOpeningTime() ) );
        }
        if ( filial.getClosingTime() != null ) {
            filialDto.closingTime( DateTimeFormatter.ofPattern( "dd.MM.yyyy HH:mm" ).format( filial.getClosingTime() ) );
        }
        filialDto.id( filial.getId() );
        filialDto.name( filial.getName() );
        filialDto.latitude( filial.getLatitude() );
        filialDto.longitude( filial.getLongitude() );
        filialDto.address( filial.getAddress() );
        filialDto.buildingSize( filial.getBuildingSize() );
        filialDto.humanCapacity( filial.getHumanCapacity() );
        filialDto.phoneNum( filial.getPhoneNum() );
        filialDto.adminId( filial.getAdminId() );
        List<Integer> list = filial.getEmployees();
        if ( list != null ) {
            filialDto.employees( new ArrayList<Integer>( list ) );
        }
        filialDto.imagePath( filial.getImagePath() );
        filialDto.active( filial.getActive() );

        return filialDto.build();
    }

    @Override
    public Filial toEntity(FilialDto filialDto) {
        if ( filialDto == null ) {
            return null;
        }

        FilialBuilder filial = Filial.builder();

        if ( filialDto.getOpeningTime() != null ) {
            filial.openingTime( LocalDateTime.parse( filialDto.getOpeningTime(), DateTimeFormatter.ofPattern( "dd.MM.yyyy HH:mm" ) ) );
        }
        if ( filialDto.getClosingTime() != null ) {
            filial.closingTime( LocalDateTime.parse( filialDto.getClosingTime(), DateTimeFormatter.ofPattern( "dd.MM.yyyy HH:mm" ) ) );
        }
        filial.id( filialDto.getId() );
        filial.name( filialDto.getName() );
        filial.latitude( filialDto.getLatitude() );
        filial.longitude( filialDto.getLongitude() );
        filial.address( filialDto.getAddress() );
        filial.buildingSize( filialDto.getBuildingSize() );
        filial.humanCapacity( filialDto.getHumanCapacity() );
        filial.phoneNum( filialDto.getPhoneNum() );
        filial.adminId( filialDto.getAdminId() );
        filial.imagePath( filialDto.getImagePath() );
        List<Integer> list = filialDto.getEmployees();
        if ( list != null ) {
            filial.employees( new ArrayList<Integer>( list ) );
        }
        filial.active( filialDto.getActive() );

        return filial.build();
    }
}
