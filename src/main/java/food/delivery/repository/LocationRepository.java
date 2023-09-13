package food.delivery.repository;

import food.delivery.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findAllByUserId(Long userId);

    List<Location> findAllByUserIdAndActive(Long userId, boolean active);
}