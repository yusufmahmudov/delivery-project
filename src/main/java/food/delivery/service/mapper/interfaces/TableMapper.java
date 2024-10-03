package food.delivery.service.mapper.interfaces;

import food.delivery.dto.TableDto;
import food.delivery.model.Table;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TableMapper {

    Table toEntity(TableDto tableDto);

    TableDto toDto(Table table);

}
