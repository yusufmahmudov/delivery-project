package food.delivery.service.mapper.interfaces;

import food.delivery.dto.template.ImageDto;
import food.delivery.dto.template.ImageDto.ImageDtoBuilder;
import food.delivery.model.Image;
import food.delivery.model.Image.ImageBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-13T19:07:17+0500",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class ImageMapperImpl implements ImageMapper {

    @Override
    public ImageDto toDto(Image image) {
        if ( image == null ) {
            return null;
        }

        ImageDtoBuilder imageDto = ImageDto.builder();

        imageDto.id( image.getId() );
        imageDto.name( image.getName() );
        imageDto.imagePath( image.getImagePath() );
        imageDto.type( image.getType() );
        imageDto.position( image.getPosition() );

        return imageDto.build();
    }

    @Override
    public Image toEntity(ImageDto imageDto) {
        if ( imageDto == null ) {
            return null;
        }

        ImageBuilder image = Image.builder();

        image.id( imageDto.getId() );
        image.name( imageDto.getName() );
        image.imagePath( imageDto.getImagePath() );
        image.type( imageDto.getType() );
        image.position( imageDto.getPosition() );

        return image.build();
    }
}
