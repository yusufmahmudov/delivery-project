package food.delivery.repository;

import food.delivery.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByName(String fileName);

//    Optional<Image> findByProductId(Integer productId);

    List<Image> findAllByPosition(String position);

}
