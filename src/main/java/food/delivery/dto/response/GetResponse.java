package food.delivery.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class GetResponse {

    private Integer count;

    private String next;

    private String previous;

    private List<?> data;
}
