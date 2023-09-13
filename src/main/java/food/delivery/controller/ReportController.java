package food.delivery.controller;

import food.delivery.dto.response.ReportDto;
import food.delivery.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;


@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
@Tag(name = "report", description = "Statistika ma'lumotlar bilan ishlovchi apilar")
@Validated
@PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_COURIER')")
public class ReportController {

    private final ReportService reportService;


    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "Umumiy uch oylik daromad",
            tags = {"report", "get"})
    @GetMapping("/three-months-income")
    public ResponseEntity<?> threeMonthsOfIncome() {
        List<ReportDto> list = reportService.threeMonthsOfIncome();
        return ResponseEntity.ok().body(list);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "Umumiy oylik daromad",
            tags = {"report", "get"})
    @GetMapping("/monthly-income")
    public ResponseEntity<?> monthlyIncome() {
        List<ReportDto> list = reportService.monthlyIncome();
        return ResponseEntity.ok().body(list);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "Umumiy haftalik daromad",
            tags = {"report", "get"})
    @GetMapping("/weekly-income")
    public ResponseEntity<?> weeklyIncome() {
        List<ReportDto> list = reportService.weeklyIncome();
        return ResponseEntity.ok().body(list);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "Umumiy kunlik daromad",
            tags = {"report", "get"})
    @GetMapping("/daily-income")
    public ResponseEntity<?> dailyIncome() {
        List<ReportDto> list = reportService.dailyIncome();
        return ResponseEntity.ok().body(list);
    }




    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "So'nggi uch oyda Productlarning sotilish darajasi, soni",
            tags = {"report", "get"})
    @GetMapping("/three-months-best-selling-food")
    public ResponseEntity<?> threeMonthsBestSellingFood() {
        List<ReportDto> list = reportService.threeMonthsBestSellingFood();
        return ResponseEntity.ok().body(list);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "So'nggi bir oyda Productlarning sotilish darajasi, soni",
            tags = {"report", "get"})
    @GetMapping("/monthly-best-selling-food")
    public ResponseEntity<?> monthlyBestSellingFood() {
        List<ReportDto> list = reportService.monthlyBestSellingFood();
        return ResponseEntity.ok().body(list);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "So'nggi bir haftada Productlarning sotilish darajasi, soni",
            tags = {"report", "get"})
    @GetMapping("/weekly-best-selling-food")
    public ResponseEntity<?> weeklyBestSellingFood() {
        List<ReportDto> list = reportService.weeklyBestSellingFood();
        return ResponseEntity.ok().body(list);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "So'nggi bir kunda Productlarning sotilish darajasi, soni",
            tags = {"report", "get"})
    @GetMapping("/daily-best-selling-food")
    public ResponseEntity<?> dailyBestSellingFood() {
        List<ReportDto> list = reportService.dailyBestSellingFood();
        return ResponseEntity.ok().body(list);
    }




    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "So'nggi uch oyda Productlarning sotilish darajasi, soni. id bo'yicha 1ta filial",
            tags = {"report", "get"})
    @GetMapping("/three-months-best-selling-food-by-id")
    public ResponseEntity<?> threeMonthsBestSellingFoodById(
            @RequestParam @Min(1) Integer id) {
        List<ReportDto> list = reportService.threeMonthsBestSellingFoodById(id);
        return ResponseEntity.ok().body(list);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "So'nggi bir oyda Productlarning sotilish darajasi, soni. id bo'yicha 1ta filial",
            tags = {"report", "get"})
    @GetMapping("/monthly-best-selling-food-by-id")
    public ResponseEntity<?> monthlyBestSellingFoodById(
            @RequestParam @Min(1) Integer id) {
        List<ReportDto> list = reportService.monthlyBestSellingFoodById(id);
        return ResponseEntity.ok().body(list);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "So'nggi bir haftada Productlarning sotilish darajasi, soni. id bo'yicha 1ta filial",
            tags = {"report", "get"})
    @GetMapping("/weekly-best-selling-food-by-id")
    public ResponseEntity<?> weeklyBestSellingFoodById(
            @RequestParam @Min(1) Integer id) {
        List<ReportDto> list = reportService.weeklyBestSellingFoodById(id);
        return ResponseEntity.ok().body(list);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "So'nggi bir kunda Productlarning sotilish darajasi, soni. id bo'yicha 1ta filial",
            tags = {"report", "get"})
    @GetMapping("/daily-best-selling-food-by-id")
    public ResponseEntity<?> dailyBestSellingFoodById(
            @RequestParam @Min(1) Integer id) {
        List<ReportDto> list = reportService.dailyBestSellingFoodById(id);
        return ResponseEntity.ok().body(list);
    }





    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "So'nggi uch oyda Kunning qaysi qismi sotuv qanday bo'lishini" +
            " ko'rsatib beruvchi api. Barcha filiallar uchun",
            tags = {"report", "get"})
    @GetMapping("/three-months-best-selling-food-time")
    public ResponseEntity<?> threeMonthsBestSellingFoodTime() {
        List<ReportDto> list = reportService.threeMonthsBestSellingFoodTime();
        return ResponseEntity.ok().body(list);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "So'nggi bir oyda Kunning qaysi qismi sotuv qanday bo'lishini" +
            " ko'rsatib beruvchi api. Barcha filiallar uchun",
            tags = {"report", "get"})
    @GetMapping("/monthly-best-selling-food-time")
    public ResponseEntity<?> monthlyBestSellingFoodTime() {
        List<ReportDto> list = reportService.monthlyBestSellingFoodTime();
        return ResponseEntity.ok().body(list);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "So'nggi bir haftada Kunning qaysi qismi sotuv qanday bo'lishini" +
            " ko'rsatib beruvchi api. Barcha filiallar uchun",
            tags = {"report", "get"})
    @GetMapping("/weekly-best-selling-food-time")
    public ResponseEntity<?> weeklyBestSellingFoodTime() {
        List<ReportDto> list = reportService.weeklyBestSellingFoodTime();
        return ResponseEntity.ok().body(list);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "So'nggi bir kunda Kunning qaysi qismi sotuv qanday bo'lishini" +
            " ko'rsatib beruvchi api. Barcha filiallar uchun",
            tags = {"report", "get"})
    @GetMapping("/daily-best-selling-food-time")
    public ResponseEntity<?> dailyBestSellingFoodTime() {
        List<ReportDto> list = reportService.dailyBestSellingFoodTime();
        return ResponseEntity.ok().body(list);
    }




    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "So'nggi uch oyda Kunning qaysi qismi sotuv qanday bo'lishini" +
            " ko'rsatib beruvchi api. id bo'yicha 1ta filial",
            tags = {"report", "get"})
    @GetMapping("/three-months-best-selling-food-time-by-id")
    public ResponseEntity<?> threeMonthsBestSellingFoodTimeById(
            @RequestParam @Min(1) Integer id) {
        List<ReportDto> list = reportService.threeMonthsBestSellingFoodTimeById(id);
        return ResponseEntity.ok().body(list);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "So'nggi bir oyda Kunning qaysi qismi sotuv qanday bo'lishini" +
            " ko'rsatib beruvchi api. id bo'yicha 1ta filial",
            tags = {"report", "get"})
    @GetMapping("/monthly-best-selling-food-time-by-id")
    public ResponseEntity<?> monthlyBestSellingFoodTimeById(
            @RequestParam @Min(1) Integer id) {
        List<ReportDto> list = reportService.monthlyBestSellingFoodTimeById(id);
        return ResponseEntity.ok().body(list);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "So'nggi bir haftada Kunning qaysi qismi sotuv qanday bo'lishini" +
            " ko'rsatib beruvchi api. id bo'yicha 1ta filial",
            tags = {"report", "get"})
    @GetMapping("/weekly-best-selling-food-time-by-id")
    public ResponseEntity<?> weeklyBestSellingFoodTimeById(
            @RequestParam @Min(1) Integer id) {
        List<ReportDto> list = reportService.weeklyBestSellingFoodTimeById(id);
        return ResponseEntity.ok().body(list);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "So'nggi bir kunda Kunning qaysi qismi sotuv qanday bo'lishini" +
            " ko'rsatib beruvchi api. id bo'yicha 1ta filial",
            tags = {"report", "get"})
    @GetMapping("/daily-best-selling-food-time-by-id")
    public ResponseEntity<?> dailyBestSellingFoodTimeById(
            @RequestParam @Min(1) Integer id) {
        List<ReportDto> list = reportService.dailyBestSellingFoodTimeById(id);
        return ResponseEntity.ok().body(list);
    }





    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "Oxirgi haftada qo'shilgan yangi foydalanuvchilar soni",
            tags = {"report", "get"})
    @GetMapping("/count-new-users-last-week")
    public ResponseEntity<?> countOfNewUsersLastWeek() {
        Long count = reportService.countOfNewUsersLastWeek();
        return ResponseEntity.ok().body(count);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "Oxirgi oyda qo'shilgan yangi foydalanuvchilar soni",
            tags = {"report", "get"})
    @GetMapping("/count-new-users-last-month")
    public ResponseEntity<?> countOfNewUsersLastMonth() {
        Long count = reportService.countOfNewUsersLastMonth();
        return ResponseEntity.ok().body(count);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "Oxirgi 3 oyda qo'shilgan yangi foydalanuvchilar soni",
            tags = {"report", "get"})
    @GetMapping("/count-new-users-last-three-month")
    public ResponseEntity<?> countOfNewUsersLastThreeMonth() {
        Long count = reportService.countOfNewUsersLastThreeMonth();
        return ResponseEntity.ok().body(count);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "Umumiy foydalanuvchilar soni",
            tags = {"report", "get"})
    @GetMapping("/count-all-users")
    public ResponseEntity<?> countOfAllUsers() {
        Long count = reportService.countOfAllUsers();
        return ResponseEntity.ok().body(count);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @Operation(summary = "Umumiy xodimlar soni",
            tags = {"report", "get"})
    @GetMapping("/count-all-employees")
    public ResponseEntity<?> countOfAllEmployees() {
        return reportService.countOfAllEmployees();
    }





    @PreAuthorize("hasRole('ROLE_COURIER')")
    @Operation(summary = "Kuryerning uch oylik daromadi",
            tags = {"report", "get"})
    @GetMapping("/three-months-income-courier")
    public ResponseEntity<?> threeMonthsIncomeOfTheCourier() {
        List<ReportDto> list = reportService.threeMonthsIncomeOfTheCourier();
        return ResponseEntity.ok().body(list);
    }


    @PreAuthorize("hasRole('ROLE_COURIER')")
    @Operation(summary = "Kuryerning oylik daromadi",
            tags = {"report", "get"})
    @GetMapping("/monthly-income-courier")
    public ResponseEntity<?> monthlyIncomeOfTheCourier() {
        List<ReportDto> list = reportService.monthlyIncomeOfTheCourier();
        return ResponseEntity.ok().body(list);
    }


    @PreAuthorize("hasRole('ROLE_COURIER')")
    @Operation(summary = "Kuryerning haftalik daromadi",
            tags = {"report", "get"})
    @GetMapping("/weekly-income-courier")
    public ResponseEntity<?> weeklyIncomeOfTheCourier() {
        List<ReportDto> list = reportService.weeklyIncomeOfTheCourier();
        return ResponseEntity.ok().body(list);
    }


//    @PreAuthorize("hasRole('USER_ROLE')")
    @Operation(summary = "Haftalik popular taomlar",
            tags = {"report", "get"})
    @GetMapping("/popular-food")
    public ResponseEntity<?> popularFoods() {
        return reportService.popularFoods();
    }

}