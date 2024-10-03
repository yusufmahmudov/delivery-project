package food.delivery.dto.template;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderReportDto {

    private Double price;

    private Integer count;
}
