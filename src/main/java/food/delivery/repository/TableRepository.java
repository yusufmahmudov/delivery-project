package food.delivery.repository;

import food.delivery.model.Employee;
import food.delivery.model.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<Table, Integer> {

    List<Table> findAllByFilialId(Integer filialId);

    List<Table> findAllByFilialIdAndBusy(Integer filialId, Boolean active);

    void deleteByFilialId(Integer filialId);

}
