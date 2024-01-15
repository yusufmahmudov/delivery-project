package food.delivery.service.mapper.interfaces;

import food.delivery.dto.FilialDto;
import food.delivery.model.Filial;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FilialMapper {

    @Mapping(target = "openingTime", source = "openingTime", dateFormat = "yyyy.MM.dd HH:mm")
    @Mapping(target = "closingTime", source = "closingTime", dateFormat = "yyyy.MM.dd HH:mm")
    FilialDto toDto(Filial filial);

    @Mapping(target = "openingTime", source = "openingTime", dateFormat = "yyyy.MM.dd HH:mm")
    @Mapping(target = "closingTime", source = "closingTime", dateFormat = "yyyy.MM.dd HH:mm")
    Filial toEntity(FilialDto filialDto);
}
