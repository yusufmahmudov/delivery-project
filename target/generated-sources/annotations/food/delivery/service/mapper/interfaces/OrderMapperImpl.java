package food.delivery.service.mapper.interfaces;

import food.delivery.dto.OrderDto;
import food.delivery.dto.OrderDto.OrderDtoBuilder;
import food.delivery.dto.OrderedProductDto;
import food.delivery.dto.OrderedProductDto.OrderedProductDtoBuilder;
import food.delivery.model.Order;
import food.delivery.model.Order.OrderBuilder;
import food.delivery.model.OrderedProduct;
import food.delivery.model.OrderedProduct.OrderedProductBuilder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-12T19:32:08+0500",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public Order toEntity(OrderDto orderDto) {
        if ( orderDto == null ) {
            return null;
        }

        OrderBuilder order = Order.builder();

        if ( orderDto.getCreatedAt() != null ) {
            order.createdAt( LocalDateTime.parse( orderDto.getCreatedAt(), DateTimeFormatter.ofPattern( "dd.MM.yyyy HH:mm" ) ) );
        }
        if ( orderDto.getOrderTime() != null ) {
            order.orderTime( LocalDateTime.parse( orderDto.getOrderTime(), DateTimeFormatter.ofPattern( "dd.MM.yyyy HH:mm" ) ) );
        }
        order.id( orderDto.getId() );
        order.userId( orderDto.getUserId() );
        order.courierId( orderDto.getCourierId() );
        order.filialId( orderDto.getFilialId() );
        order.orderNumber( orderDto.getOrderNumber() );
        order.paid( orderDto.getPaid() );
        order.paymentType( orderDto.getPaymentType() );
        order.orderType( orderDto.getOrderType() );
        order.locationId( orderDto.getLocationId() );
        order.deliveryPrice( orderDto.getDeliveryPrice() );
        order.totalPrice( orderDto.getTotalPrice() );
        order.quantity( orderDto.getQuantity() );
        order.comment( orderDto.getComment() );
        order.notice( orderDto.getNotice() );
        order.status( orderDto.getStatus() );
        order.orderProducts( orderedProductDtoListToOrderedProductList( orderDto.getOrderProducts() ) );

        return order.build();
    }

    @Override
    public OrderDto toDto(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDtoBuilder orderDto = OrderDto.builder();

        if ( order.getCreatedAt() != null ) {
            orderDto.createdAt( DateTimeFormatter.ofPattern( "dd.MM.yyyy HH:mm" ).format( order.getCreatedAt() ) );
        }
        if ( order.getOrderTime() != null ) {
            orderDto.orderTime( DateTimeFormatter.ofPattern( "dd.MM.yyyy HH:mm" ).format( order.getOrderTime() ) );
        }
        orderDto.id( order.getId() );
        orderDto.userId( order.getUserId() );
        orderDto.courierId( order.getCourierId() );
        orderDto.filialId( order.getFilialId() );
        orderDto.orderNumber( order.getOrderNumber() );
        orderDto.paid( order.getPaid() );
        orderDto.paymentType( order.getPaymentType() );
        orderDto.orderType( order.getOrderType() );
        orderDto.locationId( order.getLocationId() );
        orderDto.deliveryPrice( order.getDeliveryPrice() );
        orderDto.totalPrice( order.getTotalPrice() );
        orderDto.quantity( order.getQuantity() );
        orderDto.comment( order.getComment() );
        orderDto.notice( order.getNotice() );
        orderDto.status( order.getStatus() );
        orderDto.orderProducts( orderedProductListToOrderedProductDtoList( order.getOrderProducts() ) );

        return orderDto.build();
    }

    protected OrderedProduct orderedProductDtoToOrderedProduct(OrderedProductDto orderedProductDto) {
        if ( orderedProductDto == null ) {
            return null;
        }

        OrderedProductBuilder orderedProduct = OrderedProduct.builder();

        orderedProduct.orderId( orderedProductDto.getOrderId() );
        orderedProduct.productId( orderedProductDto.getProductId() );
        orderedProduct.price( orderedProductDto.getPrice() );
        orderedProduct.discount( orderedProductDto.getDiscount() );
        orderedProduct.discountPrice( orderedProductDto.getDiscountPrice() );
        orderedProduct.totalPrice( orderedProductDto.getTotalPrice() );
        orderedProduct.amount( orderedProductDto.getAmount() );

        return orderedProduct.build();
    }

    protected List<OrderedProduct> orderedProductDtoListToOrderedProductList(List<OrderedProductDto> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderedProduct> list1 = new ArrayList<OrderedProduct>( list.size() );
        for ( OrderedProductDto orderedProductDto : list ) {
            list1.add( orderedProductDtoToOrderedProduct( orderedProductDto ) );
        }

        return list1;
    }

    protected OrderedProductDto orderedProductToOrderedProductDto(OrderedProduct orderedProduct) {
        if ( orderedProduct == null ) {
            return null;
        }

        OrderedProductDtoBuilder orderedProductDto = OrderedProductDto.builder();

        orderedProductDto.orderId( orderedProduct.getOrderId() );
        orderedProductDto.productId( orderedProduct.getProductId() );
        orderedProductDto.price( orderedProduct.getPrice() );
        orderedProductDto.discount( orderedProduct.getDiscount() );
        orderedProductDto.discountPrice( orderedProduct.getDiscountPrice() );
        orderedProductDto.totalPrice( orderedProduct.getTotalPrice() );
        orderedProductDto.amount( orderedProduct.getAmount() );

        return orderedProductDto.build();
    }

    protected List<OrderedProductDto> orderedProductListToOrderedProductDtoList(List<OrderedProduct> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderedProductDto> list1 = new ArrayList<OrderedProductDto>( list.size() );
        for ( OrderedProduct orderedProduct : list ) {
            list1.add( orderedProductToOrderedProductDto( orderedProduct ) );
        }

        return list1;
    }
}
