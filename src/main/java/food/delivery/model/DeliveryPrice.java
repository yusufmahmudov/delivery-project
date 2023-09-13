package food.delivery.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "delivery_price")
public class DeliveryPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "delivery_price_id_seq")
    @SequenceGenerator(name = "delivery_price_id_seq", sequenceName = "delivery_price_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @Column(name = "price")
    private Double price;

    @Column(name = "price_out")
    private Double priceOut;

    @Column(name = "distance_price")
    private Double distancePrice;

    @Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean active;

}
