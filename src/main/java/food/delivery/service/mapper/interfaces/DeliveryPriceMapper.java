package food.delivery.service.mapper.interfaces;

import food.delivery.dto.DeliveryPriceDto;
import food.delivery.model.DeliveryPrice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeliveryPriceMapper {

    DeliveryPriceDto toDto(DeliveryPrice deliveryPrice);
    DeliveryPrice toEntity(DeliveryPriceDto deliveryPriceDto);
}
