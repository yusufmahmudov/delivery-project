package food.delivery.service.mapper.interfaces;

import food.delivery.dto.DeliveryPriceDto;
import food.delivery.dto.DeliveryPriceDto.DeliveryPriceDtoBuilder;
import food.delivery.model.DeliveryPrice;
import food.delivery.model.DeliveryPrice.DeliveryPriceBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-13T19:07:17+0500",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class DeliveryPriceMapperImpl implements DeliveryPriceMapper {

    @Override
    public DeliveryPriceDto toDto(DeliveryPrice deliveryPrice) {
        if ( deliveryPrice == null ) {
            return null;
        }

        DeliveryPriceDtoBuilder deliveryPriceDto = DeliveryPriceDto.builder();

        deliveryPriceDto.id( deliveryPrice.getId() );
        deliveryPriceDto.price( deliveryPrice.getPrice() );
        deliveryPriceDto.priceOut( deliveryPrice.getPriceOut() );
        deliveryPriceDto.distancePrice( deliveryPrice.getDistancePrice() );
        deliveryPriceDto.active( deliveryPrice.getActive() );

        return deliveryPriceDto.build();
    }

    @Override
    public DeliveryPrice toEntity(DeliveryPriceDto deliveryPriceDto) {
        if ( deliveryPriceDto == null ) {
            return null;
        }

        DeliveryPriceBuilder deliveryPrice = DeliveryPrice.builder();

        deliveryPrice.id( deliveryPriceDto.getId() );
        deliveryPrice.price( deliveryPriceDto.getPrice() );
        deliveryPrice.priceOut( deliveryPriceDto.getPriceOut() );
        deliveryPrice.distancePrice( deliveryPriceDto.getDistancePrice() );
        deliveryPrice.active( deliveryPriceDto.getActive() );

        return deliveryPrice.build();
    }
}
