package food.delivery.service.mapper.interfaces;

import food.delivery.dto.OrderDto;
import food.delivery.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "dd.MM.yyyy HH:mm")
    @Mapping(target = "orderTime", source = "orderTime", dateFormat = "dd.MM.yyyy HH:mm")
    Order toEntity(OrderDto orderDto);

    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "dd.MM.yyyy HH:mm")
    @Mapping(target = "orderTime", source = "orderTime", dateFormat = "dd.MM.yyyy HH:mm")
    OrderDto toDto(Order order);

}
