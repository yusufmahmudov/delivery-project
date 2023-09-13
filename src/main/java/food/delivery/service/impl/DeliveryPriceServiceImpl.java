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
    public ResponseDto<String> addNewPrice(DeliveryPriceDto deliveryPriceDto) {
        try {
            DeliveryPrice deliveryPrice = deliveryPriceMapper.toEntity(deliveryPriceDto);
            deliveryPriceRepository.save(deliveryPrice);

            return ResponseDto.<String>builder()
                    .success(true)
                    .code(AppCode.OK)
                    .message(AppMessages.SAVED)
                    .build();
        }catch (DataAccessException | PersistenceException | IllegalArgumentException e){
            log.error(e.getMessage());
            return ResponseDto.<String>builder()
                    .code(AppCode.ERROR)
                    .success(false)
                    .message(e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<DeliveryPriceDto> getDeliveryPrice() {
        try {
            DeliveryPrice deliveryPrice = deliveryPriceRepository.findTopByOrderByIdDesc();
            DeliveryPriceDto deliveryPriceDto = deliveryPriceMapper.toDto(deliveryPrice);

            return ResponseDto.<DeliveryPriceDto>builder()
                    .data(deliveryPriceDto)
                    .message(AppMessages.OK)
                    .success(true)
                    .code(AppCode.OK)
                    .build();
        }catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseDto.<DeliveryPriceDto>builder()
                    .success(false)
                    .code(AppCode.ERROR)
                    .message(e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseEntity<?> getPrice() {
        ResponseDto<DeliveryPriceDto> responseDto = getDeliveryPrice();
        Double price = responseDto.getData().getPrice();
        return ResponseEntity.ok().body(price);
    }
}
