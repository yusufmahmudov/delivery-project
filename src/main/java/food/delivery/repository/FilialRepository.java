package food.delivery.repository;

import food.delivery.model.Filial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilialRepository extends JpaRepository<Filial, Integer> {

    List<Filial> findAllByActive(boolean active);

}
