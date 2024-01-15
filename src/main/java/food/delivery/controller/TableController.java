package food.delivery.controller;

import food.delivery.service.TableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@Tag(name = "table", description = "Filial stollari uchun API lar")
@RestController
@RequestMapping("/table")
@RequiredArgsConstructor
@Validated
public class TableController  {

    private final TableService tableService;


    @Operation(summary = "Filialning barcha stol mahsulotlarni chiqarish",
            tags = {"table", "get"})
    @GetMapping("/get-all-by-filial-id")
    public ResponseEntity<?> allTableByFilial(
            @RequestParam(value = "filialId") Integer filialId) {
        return tableService.allTableByFilial(filialId);
    }


    @Operation(summary = "Id bo'yicha 1ta stolni chiqarish",
            tags = {"table", "get"})
    @GetMapping("/by-id")
    public ResponseEntity<?> getById(
            @RequestParam(value = "id") Integer id) {
        return tableService.getById(id);
    }


    @Operation(summary = "Filialning barcha active yoki passiv stollari ro'yxati. active- true or false",
            tags = {"table", "get"})
    @GetMapping("/get-all-by-active")
    public ResponseEntity<?> allTableByActive(
            @RequestParam(value = "filialId") Integer filialId,
            @RequestParam(value = "active") Boolean active) {
        return tableService.allTableByActive(filialId, active);
    }


    @Operation(summary = "Stolni id bo'yicha o'chirish",
            tags = {"table", "delete"})
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTable(
            @RequestParam(value = "id") Integer id) {
        return tableService.deleteTable(id);
    }


    @Operation(summary = "Id bo'yicha stolni band qilish yoki aksincha. active true or false",
            tags = {"table", "get"})
    @PatchMapping("/is-active")
    public ResponseEntity<?> isActive(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "active") Boolean active) {
        return tableService.isActive(id, active);
    }


    @Transactional
    @Operation(summary = "Filialning stollar sonini o'zgartirish")
    @PutMapping("/update")
    public ResponseEntity<?> update(
            @RequestParam(value = "filialId") Integer filialId,
            @RequestParam(value = "count") Integer count
    ) {
        return tableService.updateFilialTables(filialId, count);
    }

}
