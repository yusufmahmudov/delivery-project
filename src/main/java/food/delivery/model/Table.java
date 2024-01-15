package food.delivery.model;


import food.delivery.dto.FilialDto;
import food.delivery.helper.AppMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@javax.persistence.Table(name = "tables")
public class Table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "table_id_seq")
    @SequenceGenerator(name = "table_id_seq", sequenceName = "table_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;


    @Column(name = "table_no")
    private Integer tableNo;


    @Column(name = "busy")
    private Boolean busy;


    @Column(name = "filialId")
    private Integer filialId;

}
