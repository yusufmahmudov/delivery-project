package food.delivery.service.mapper.interfaces;

import food.delivery.dto.TableDto;
import food.delivery.dto.TableDto.TableDtoBuilder;
import food.delivery.model.Table;
import food.delivery.model.Table.TableBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-15T00:07:59+0500",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class TableMapperImpl implements TableMapper {

    @Override
    public Table toEntity(TableDto tableDto) {
        if ( tableDto == null ) {
            return null;
        }

        TableBuilder table = Table.builder();

        table.id( tableDto.getId() );
        table.tableNo( tableDto.getTableNo() );
        table.busy( tableDto.getBusy() );
        table.filialId( tableDto.getFilialId() );

        return table.build();
    }

    @Override
    public TableDto toDto(Table table) {
        if ( table == null ) {
            return null;
        }

        TableDtoBuilder tableDto = TableDto.builder();

        tableDto.id( table.getId() );
        tableDto.tableNo( table.getTableNo() );
        tableDto.busy( table.getBusy() );
        tableDto.filialId( table.getFilialId() );

        return tableDto.build();
    }
}
