package food.delivery.model;

import javax.persistence.*;
import javax.persistence.Table;

import food.delivery.dto.TableDto;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Table(name = "filial")
public class Filial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "filial_id_seq")
    @SequenceGenerator(name = "filial_id_seq", sequenceName = "filial_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "address")
    private String address;

    @Column(name = "building_size")
    private String buildingSize;

    @Column(name = "human_capacity")
    private Integer humanCapacity;

    @Column(name = "table_count")
    private Integer tableCount;

    @Column(name = "servicing")
    private Double servicing;

    @Column(name = "phone_num")
    private String phoneNum;

    @Column(name = "opening_time")
    private LocalDateTime openingTime;

    @Column(name = "closing_time")
    private LocalDateTime closingTime;

    @Column(name = "admin_id")
    private Integer adminId;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "image_id")
    private Long imageId;

    @ElementCollection
    private List<Integer> employees;

    @Column(name = "active")
    private Boolean active;
}