package food.delivery.model;


import javax.persistence.*;
import javax.persistence.Table;

import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "ordered_products")
public class OrderedProduct {

    @Id
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "price")
    private Double price;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "discount_price")
    private Double discountPrice;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "amount")
    private Integer amount;

}
