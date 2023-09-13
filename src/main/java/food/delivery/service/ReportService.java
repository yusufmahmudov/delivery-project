package food.delivery.service;


import food.delivery.dto.response.ReportDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReportService {


    /** Barcha filiallarning umumiy so'ngi uch oydagi daromadlari */
    List<ReportDto> threeMonthsOfIncome();


    /** Barcha filiallarning umumiy so'ngi oydagi daromadlari */
    List<ReportDto> monthlyIncome();


    /** Barcha filiallarning umumiy so'ngi haftadagi daromadlari */
    List<ReportDto> weeklyIncome();


    /** Barcha filiallarning umumiy so'ngi kundagi daromadlari */
    List<ReportDto> dailyIncome();





    /** So'nggi uch oyda Productlarning sotilish darajasi, soni */
    List<ReportDto> threeMonthsBestSellingFood();


    /** So'nggi bir oyda Productlarning sotilish darajasi, soni */
    List<ReportDto> monthlyBestSellingFood();


    /** So'nggi bir haftada Productlarning sotilish darajasi, soni */
    List<ReportDto> weeklyBestSellingFood();


    /** So'nggi bir kunda Productlarning sotilish darajasi, soni */
    List<ReportDto> dailyBestSellingFood();





    /** So'nggi uch oyda Productlarning sotilish darajasi, soni. id bo'yicha 1ta filial */
    List<ReportDto> threeMonthsBestSellingFoodById(Integer id);


    /** So'nggi bir oyda Productlarning sotilish darajasi, soni. id bo'yicha 1ta filial */
    List<ReportDto> monthlyBestSellingFoodById(Integer id);


    /** So'nggi bir haftada Productlarning sotilish darajasi, soni. id bo'yicha 1ta filial */
    List<ReportDto> weeklyBestSellingFoodById(Integer id);


    /** So'nggi bir kunda  Productlarning sotilish darajasi, soni. id bo'yicha 1ta filial */
    List<ReportDto> dailyBestSellingFoodById(Integer id);





    /** So'nggi uch oyda Kunning qaysi qismi sotuv qanday bo'lishi ko'rsatib beruvchi method.
     *  Barcha filiallar uchun */
    List<ReportDto> threeMonthsBestSellingFoodTime();


    /** So'nggi bir oyda Kunning qaysi qismi sotuv qanday bo'lishi ko'rsatib beruvchi method.
     *  Barcha filiallar uchun */
    List<ReportDto> monthlyBestSellingFoodTime();


    /** So'nggi bir haftada Kunning qaysi qismi sotuv qanday bo'lishi ko'rsatib beruvchi method.
     *  Barcha filiallar uchun */
    List<ReportDto> weeklyBestSellingFoodTime();


    /** So'nggi bir kunda Kunning qaysi qismi sotuv qanday bo'lishi ko'rsatib beruvchi method.
     *  Barcha filiallar uchun */
    List<ReportDto> dailyBestSellingFoodTime();





    /** So'nggi uch oyda Kunning qaysi qismi sotuv qanday bo'lishi ko'rsatib beruvchi method.
     *  id bo'yicha 1ta filial */
    List<ReportDto> threeMonthsBestSellingFoodTimeById(Integer id);


    /** So'nggi bir oyda Kunning qaysi qismi sotuv qanday bo'lishi ko'rsatib beruvchi method.
     *  id bo'yicha 1ta filial */
    List<ReportDto> monthlyBestSellingFoodTimeById(Integer id);


    /** So'nggi bir haftada Kunning qaysi qismi sotuv qanday bo'lishi ko'rsatib beruvchi method.
     *  id bo'yicha 1ta filial */
    List<ReportDto> weeklyBestSellingFoodTimeById(Integer id);


    /** So'nggi bir kunda Kunning qaysi qismi sotuv qanday bo'lishi ko'rsatib beruvchi method.
     *  id bo'yicha 1ta filial */
    List<ReportDto> dailyBestSellingFoodTimeById(Integer id);





    /** So'nggi bir haftada qo'shilgan foydalanuvchilar soni */
    Long countOfNewUsersLastWeek();


    /** So'nggi bir oyda qo'shilgan foydalanuvchilar soni */
    Long countOfNewUsersLastMonth();


    /** So'nggi uch oyda qo'shilgan foydalanuvchilar soni */
    Long countOfNewUsersLastThreeMonth();


    /** Barcha foydalanuvchilar sonini qaytaruvchi method */
    Long countOfAllUsers();


    /** Xodim turlari va sonini qaytaruvchi method */
    ResponseEntity<?> countOfAllEmployees();





    /** Courierning so'ngi uch oylik daromadini qaytaruvchi method */
    List<ReportDto> threeMonthsIncomeOfTheCourier();


    /** Courierning so'ngi bir oylik daromadini qaytaruvchi method */
    List<ReportDto> monthlyIncomeOfTheCourier();


    /** Courierning so'ngi bir haftalik daromadini qaytaruvchi method */
    List<ReportDto> weeklyIncomeOfTheCourier();





    /** Eng ko'p sotilgan taomlar. Oylik */
    ResponseEntity<?> popularFoods();

}