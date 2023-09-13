package food.delivery.service.mapper.interfaces;

import food.delivery.dto.FilialDto;
import food.delivery.model.Filial;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FilialMapper {

    @Mapping(target = "openingTime", source = "openingTime", dateFormat = "dd.MM.yyyy HH:mm")
    @Mapping(target = "closingTime", source = "closingTime", dateFormat = "dd.MM.yyyy HH:mm")
    FilialDto toDto(Filial filial);

    @Mapping(target = "openingTime", source = "openingTime", dateFormat = "dd.MM.yyyy HH:mm")
    @Mapping(target = "closingTime", source = "closingTime", dateFormat = "dd.MM.yyyy HH:mm")
    Filial toEntity(FilialDto filialDto);
}
