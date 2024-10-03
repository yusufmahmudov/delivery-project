package food.delivery.service;

import food.delivery.dto.response.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface TableService {

    ResponseDto<?> addTable(Integer filialId, Integer count);

    ResponseEntity<?> allTableByFilial(Integer filialId);

    ResponseEntity<?> getById(Integer id);

    ResponseEntity<?> allTableByActive(Integer filialId, Boolean active);

    ResponseEntity<?> deleteTable(Integer id);

    ResponseEntity<?> isActive(Integer id, Boolean active);

    ResponseEntity<?> updateFilialTables(Integer filialId, Integer coutn);
}
