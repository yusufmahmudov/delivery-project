package food.delivery.service.mapper.interfaces;

import food.delivery.dto.template.ImageDto;
import food.delivery.model.Image;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ImageMapper {

    ImageDto toDto(Image image);
    Image toEntity(ImageDto imageDto);
}
