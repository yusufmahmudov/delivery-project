package food.delivery.model;

import javax.persistence.*;
import javax.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "order_id_seq")
    @SequenceGenerator(name = "order_id_seq", sequenceName = "order_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "courier_id")
    private Integer courierId;

    @Column(name = "employeeId")
    private Integer employeeId;

    @Column(name = "cashierId")
    private Integer cashierId;

    @Column(name = "servicingPriceId")
    private Double servicingPrice;

    @Column(name = "filial_id")
    private Integer filialId;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "table_number")
    private Integer tableNumber;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "delivery_price")
    private Double deliveryPrice;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "comment")
    private String comment;

    @Column(name = "notice")
    private String notice;

    @Column(name = "status")
    private String status;

    @Column(name = "order_time")
    private LocalDateTime orderTime;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "order_completed_time")
    private LocalDateTime orderCompletedTime;

    @OneToMany(mappedBy = "orderId")
    @ToString.Exclude
    private List<OrderedProduct> orderProducts;

}
