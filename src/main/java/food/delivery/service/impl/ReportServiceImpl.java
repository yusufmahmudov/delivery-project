package food.delivery.service.impl;

import food.delivery.dto.OrderDto;
import food.delivery.dto.ProductDto;
import food.delivery.dto.response.ReportDto;
import food.delivery.dto.template.OrderReportDto;
import food.delivery.model.Order;
import food.delivery.model.OrderedProduct;
import food.delivery.repository.*;
import food.delivery.security.SecurityUtil;
import food.delivery.service.ReportService;
import food.delivery.service.mapper.ProductMapper;
import food.delivery.service.mapper.interfaces.OrderMapper;
import food.delivery.service.mapper.interfaces.OrderedProductsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final EmployeeRepository employeeRepository;

    private final OrderMapper orderMapper;

    private final OrderedProductsRepository orderedProductsRepository;

    private final OrderedProductsMapper orderedProductsMapper;

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final FilialRepository filialRepository;


    public List<ReportDto> calculateIncome(LocalDateTime startTime, LocalDateTime endTime) {

        List<OrderDto> orders = orderRepository.findByCreatedAtBetween(startTime, endTime)
                .stream().map(orderMapper::toDto).toList();

        double totalPrice = 0;
        Integer totalQuantity = 0;
        List<ReportDto> reports = new ArrayList<>();
        SortedMap<String, OrderReportDto> map = new TreeMap<>();

        for (OrderDto o : orders) {
            Integer filialId = o.getFilialId();
            String name = filialRepository.findById(filialId).get().getName();
            Double price = o.getTotalPrice() - o.getDeliveryPrice();
            Integer count = o.getQuantity();
            totalPrice += price;
            totalQuantity += count;
            OrderReportDto dto;
            if (map.containsKey(name)) {
                dto = map.get(name);
                dto.setPrice(dto.getPrice() + price);
                dto.setCount(dto.getCount() + count);
            } else {
                dto = new OrderReportDto(price, count);
            }
            map.put(name, dto);
        }

        reports.add(new ReportDto("Total Info", totalPrice, totalQuantity, startTime, endTime));

        for (Map.Entry<String, OrderReportDto> entry : map.entrySet()) {
            Integer quantity = map.get(entry.getKey()).getCount();
            Double price = map.get(entry.getKey()).getPrice();
            reports.add(new ReportDto(entry.getKey(), price, quantity, startTime, endTime));
        }

        return reports;
    }


    @Override
    public List<ReportDto> incomeByDate(LocalDateTime startTime, LocalDateTime endTime) {
        return calculateIncome(startTime, endTime);
    }


    @Override
    public List<ReportDto> threeMonthsOfIncome() {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusMonths(3);

        return calculateIncome(startTime, endTime);
    }


    @Override
    public List<ReportDto> monthlyIncome() {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusMonths(1);

        return calculateIncome(startTime, endTime);
    }


    @Override
    public List<ReportDto> weeklyIncome() {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusWeeks(1);

        return calculateIncome(startTime, endTime);
    }


    @Override
    public List<ReportDto> dailyIncome() {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusDays(1);

        return calculateIncome(startTime, endTime);
    }


    @Override
    public List<ReportDto> bestSellingFoodToDate(LocalDateTime startTime, LocalDateTime endTime) {
        return calculateIncome(startTime, endTime);
    }


    @Override
    public List<ReportDto> threeMonthsBestSellingFood() {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusMonths(3);

        return calculateIncome(startTime, endTime);
    }


    @Override
    public List<ReportDto> monthlyBestSellingFood() {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusMonths(1);

        return calculateIncome(startTime, endTime);
    }


    @Override
    public List<ReportDto> weeklyBestSellingFood() {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusWeeks(1);

        return calculateIncome(startTime, endTime);
    }


    @Override
    public List<ReportDto> dailyBestSellingFood() {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusDays(1);

        return calculateIncome(startTime, endTime);
    }


    @Override
    public List<ReportDto> bestSellingFoodByIdAndDate(LocalDateTime startTime, LocalDateTime endTime, Integer id) {
        return calculateIncome(startTime, endTime);
    }


    @Override
    public List<ReportDto> threeMonthsBestSellingFoodById(Integer id) {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusMonths(3);

        return calculateIncome(startTime, endTime);
    }


    @Override
    public List<ReportDto> monthlyBestSellingFoodById(Integer id) {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusMonths(1);

        return calculateIncome(startTime, endTime);
    }


    @Override
    public List<ReportDto> weeklyBestSellingFoodById(Integer id) {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusWeeks(1);

        return calculateIncome(startTime, endTime);
    }


    @Override
    public List<ReportDto> dailyBestSellingFoodById(Integer id) {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusDays(1);

        return calculateIncome(startTime, endTime);
    }

    @Override
    public List<ReportDto> bestSellingFoodTimeByDate(LocalDateTime startTime, LocalDateTime endTime) {
        return calculateIncome(startTime, endTime);
    }


    @Override
    public List<ReportDto> threeMonthsBestSellingFoodTime() {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusMonths(3);

        return calculateIncome(startTime, endTime);
    }


    @Override
    public List<ReportDto> monthlyBestSellingFoodTime() {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusMonths(1);

        return calculateIncome(startTime, endTime);
    }


    @Override
    public List<ReportDto> weeklyBestSellingFoodTime() {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusWeeks(1);

        return calculateIncome(startTime, endTime);
    }


    @Override
    public List<ReportDto> dailyBestSellingFoodTime() {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusDays(1);

        return calculateIncome(startTime, endTime);
    }

    @Override
    public List<ReportDto> bestSellingFoodTimeByIdAndDate(LocalDateTime startTime, LocalDateTime endTime, Integer id) {
        return calculateIncome(startTime, endTime);
    }


    @Override
    public List<ReportDto> threeMonthsBestSellingFoodTimeById(Integer id) {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusMonths(3);

        return calculateIncome(startTime, endTime);
    }


    @Override
    public List<ReportDto> monthlyBestSellingFoodTimeById(Integer id) {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusMonths(1);

        return calculateIncome(startTime, endTime);
    }


    @Override
    public List<ReportDto> weeklyBestSellingFoodTimeById(Integer id) {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusWeeks(1);

        return calculateIncome(startTime, endTime);
    }


    @Override
    public List<ReportDto> dailyBestSellingFoodTimeById(Integer id) {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusDays(1);

        return calculateIncome(startTime, endTime);
    }


    @Override
    public Long countOfNewUserByDate(LocalDateTime startTime, LocalDateTime endTime) {
        return userRepository.countByCreatedAtBetween(startTime, endTime);
    }


    @Override
    public Long countOfNewUsersLastWeek() {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusWeeks(1);
        return userRepository.countByCreatedAtBetween(startTime, endTime);
    }


    @Override
    public Long countOfNewUsersLastMonth() {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusMonths(1);
        return userRepository.countByCreatedAtBetween(startTime, endTime);
    }


    @Override
    public Long countOfNewUsersLastThreeMonth() {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusMonths(3);
        return userRepository.countByCreatedAtBetween(startTime, endTime);
    }


    @Override
    public Long countOfAllUsers() {
        return userRepository.count();
    }


    @Override
    public ResponseEntity<?> countOfAllEmployees() {
        Map<String, Integer> map = employeeRepository.countEmployeesByWorkplaceMap();
        return ResponseEntity.ok().body(map);
    }


    @Override
    public List<ReportDto> courierIncomeByDate(LocalDateTime startTime, LocalDateTime endTime) {
        // TODO : sananing kunlari bo'yicha bo'lib tashlash. for orqali
        Integer id = Math.toIntExact(SecurityUtil.getEmployeeDto().getId());

        List<OrderDto> orders = orderRepository
                .findByCourierIdAndCreatedAtBetween(id, startTime, endTime)
                .stream().map(orderMapper::toDto).toList();

        List<ReportDto> reports = new ArrayList<>();
        for (int i = 2; i >= 0; i--) {
            endTime = LocalDateTime.now().minusMonths(i);
            startTime = endTime.minusMonths(1);
            ReportDto report = calculateCourierInfo(orders, startTime, endTime);
            reports.add(report);
        }

        return reports;
    }


    @Override
    public List<ReportDto> threeMonthsIncomeOfTheCourier() {
        Integer id = Math.toIntExact(SecurityUtil.getEmployeeDto().getId());
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusMonths(3);

        List<OrderDto> orders = orderRepository
                .findByCourierIdAndCreatedAtBetween(id, startTime, endTime)
                .stream().map(orderMapper::toDto).toList();

        List<ReportDto> reports = new ArrayList<>();
        for (int i = 2; i >= 0; i--) {
            endTime = LocalDateTime.now().minusMonths(i);
            startTime = endTime.minusMonths(1);
            ReportDto report = calculateCourierInfo(orders, startTime, endTime);
            reports.add(report);
        }

        return reports;
    }


    @Override
    public List<ReportDto> monthlyIncomeOfTheCourier() {
        Integer id = Math.toIntExact(SecurityUtil.getEmployeeDto().getId());
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusMonths(1);

        List<OrderDto> orders = orderRepository
                .findByCourierIdAndCreatedAtBetween(id, startTime, endTime)
                .stream().map(orderMapper::toDto).toList();

        List<ReportDto> reports = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {
            endTime = LocalDateTime.now().minusWeeks(i);
            startTime = endTime.minusWeeks(1);
            ReportDto report = calculateCourierInfo(orders, startTime, endTime);
            reports.add(report);
        }

        return reports;
    }


    @Override
    public List<ReportDto> weeklyIncomeOfTheCourier() {
        Integer id = Math.toIntExact(SecurityUtil.getEmployeeDto().getId());
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusWeeks(1);

        List<OrderDto> orders = orderRepository
                .findByCourierIdAndCreatedAtBetween(id, startTime, endTime)
                .stream().map(orderMapper::toDto).toList();

        List<ReportDto> reports = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            endTime = LocalDateTime.now().minusDays(i);
            startTime = endTime.minusDays(1);
            ReportDto report = calculateCourierInfo(orders, startTime, endTime);
            reports.add(report);
        }

        return reports;
    }


    public ReportDto calculateCourierInfo(List<OrderDto> orders, LocalDateTime start, LocalDateTime end) {

        ReportDto report = new ReportDto();
//        report.setPrice(0.0);
//        report.setQuantity(0);

        for (OrderDto dto : orders) {
            LocalDateTime dateTime = LocalDateTime.parse(dto.getCreatedAt());
            if (dateTime.isAfter(start) && dateTime.isBefore(end)) {
//                report.setPrice(report.getPrice() + dto.getDeliveryPrice());
//                report.setQuantity(report.getQuantity() + dto.getQuantity());
                report.plusPrice(dto.getDeliveryPrice());
                report.plusQuantity(dto.getQuantity());
            }
        }
        report.setStartTime(start);
        report.setEndTime(end);

        return report;
    }


    @Override
    public ResponseEntity<?> popularFoods() {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusMonths(1);

        List<Order> orders = orderRepository.findByCreatedAtBetween(startTime, endTime);
//                .stream().map(orderMapper::toDto).toList();

        Map<Integer, Integer> countMap = new HashMap<>();

//        for (OrderDto o : orders) {
//            List<OrderedProductDto> list = orderedProductsRepository.findAllByOrderId(o.getId())
//                    .stream().map(orderedProductsMapper::toDto).toList();
//        }

        for (Order order : orders) {
            for (OrderedProduct o : order.getOrderProducts()) {
                Integer productId = o.getProductId();
                Integer amount = countMap.getOrDefault(productId, 0);
                countMap.put(productId, amount + o.getAmount());
            }
        }

        List<Integer> productIds = countMap.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .limit(6).map(Map.Entry::getKey).toList();

        List<ProductDto> products = productRepository.findByIdIn(productIds)
                .stream().map(ProductMapper::toDto).toList();

        return ResponseEntity.ok().body(products);
    }

}
