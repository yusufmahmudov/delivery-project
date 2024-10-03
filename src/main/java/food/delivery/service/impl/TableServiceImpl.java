package food.delivery.service.impl;


import food.delivery.dto.TableDto;
import food.delivery.dto.response.ResponseDto;
import food.delivery.model.Filial;
import food.delivery.model.Table;
import food.delivery.repository.FilialRepository;
import food.delivery.repository.TableRepository;
import food.delivery.service.TableService;
import food.delivery.service.mapper.interfaces.TableMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    private final TableRepository tableRepository;
    private final FilialRepository filialRepository;
    private final TableMapper tableMapper;


    @Override
    public ResponseDto<?> addTable(Integer filialId, Integer count) {
        try {
            for (int i = 0; i < count; i++) {
                Table table = new Table();
                table.setTableNo(i+1);
                table.setFilialId(filialId);
                table.setBusy(true);
                tableRepository.save(table);
            }
            return ResponseDto.builder()
                    .data(count)
                    .build();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseDto.builder().build();
        }
    }


    @Override
    public ResponseEntity<?> allTableByFilial(Integer filialId) {
        try {
            List<TableDto> tables = tableRepository.findAllByFilialId(filialId)
                    .stream().map(tableMapper::toDto).toList();

            return ResponseEntity.ok(tables);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> getById(Integer id) {
        try {
            TableDto tableDto = tableMapper.toDto(tableRepository.findById(id).get());
            return ResponseEntity.ok().body(tableDto);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> allTableByActive(Integer filialId, Boolean active) {
        try {
            List<TableDto> tables = tableRepository.findAllByFilialIdAndBusy(filialId, active)
                    .stream().map(tableMapper::toDto).toList();
            return ResponseEntity.ok().body(tables);
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> deleteTable(Integer id) {
        try {
            Optional<Table> optional = tableRepository.findById(id);
            if (optional.isEmpty()) {
                return ResponseEntity.ok().body("Mavjud emas");
            }
            tableRepository.deleteById(id);
            Filial filial = filialRepository.findById(optional.get().getFilialId()).get();
            filial.setTableCount(filial.getTableCount()-1);
            filialRepository.save(filial);
            return ResponseEntity.ok().body("Deleted!");
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> isActive(Integer id, Boolean active) {
        try {
            Table table = tableRepository.findById(id).get();
            table.setBusy(active);
            tableRepository.save(table);

            return ResponseEntity.ok().body(tableMapper.toDto(table));
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Transactional
    @Override
    public ResponseEntity<?> updateFilialTables(Integer filialId, Integer count) {
        try {
            tableRepository.deleteByFilialId(filialId);

            addTable(filialId, count);
            Filial filial = filialRepository.findById(filialId).get();
            filial.setTableCount(count);
            filialRepository.save(filial);

            return ResponseEntity.ok().body("Saved");
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
