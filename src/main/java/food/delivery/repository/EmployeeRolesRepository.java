package food.delivery.repository;

import food.delivery.model.EmployeeRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRolesRepository extends JpaRepository<EmployeeRoles, Integer> {

    List<EmployeeRoles> findByEmployeeId(Integer employeeId);
}
