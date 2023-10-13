package food.delivery.service.impl;

import food.delivery.dto.DeliveryPriceDto;
import food.delivery.dto.response.ResponseDto;
import food.delivery.helper.AppCode;
import food.delivery.helper.AppMessages;
import food.delivery.model.DeliveryPrice;
import food.delivery.repository.DeliveryPriceRepository;
import food.delivery.service.DeliveryPriceService;
import food.delivery.service.mapper.interfaces.DeliveryPriceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;


@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryPriceServiceImpl implements DeliveryPriceService {

    private final DeliveryPriceRepository deliveryPriceRepository;
    private final DeliveryPriceMapper deliveryPriceMapper;

    @Override
    public ResponseEntity<?> addNewPrice(DeliveryPriceDto deliveryPriceDto) {
        try {
            DeliveryPrice deliveryPrice = deliveryPriceMapper.toEntity(deliveryPriceDto);
            deliveryPriceRepository.save(deliveryPrice);

            return ResponseEntity.ok().body(deliveryPrice);
        }catch (DataAccessException | PersistenceException | IllegalArgumentException e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getDeliveryPrice() {
        try {
            DeliveryPrice deliveryPrice = deliveryPriceRepository.findTopByOrderByIdDesc();
            DeliveryPriceDto deliveryPriceDto = deliveryPriceMapper.toDto(deliveryPrice);

            return ResponseEntity.ok().body(deliveryPriceDto);
        }catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getPrice() {
        ResponseEntity<?> response = getDeliveryPrice();
        Double price = (Double) response.getBody();
        return ResponseEntity.ok().body(price);
    }
}
