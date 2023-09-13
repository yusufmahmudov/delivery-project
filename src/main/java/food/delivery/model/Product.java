package food.delivery.model;

import javax.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "product_id_seq")
    @SequenceGenerator(name = "product_id_seq", sequenceName = "product_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", unique = true, length = 120)
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "is_active")
    private Boolean active;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "extra")
    private Boolean extra;

    @Column(name = "description", length = 512)
    private String description;

    @ElementCollection
    private List<Integer> extraList;

    @ElementCollection
    private List<Integer> bonusList;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

}