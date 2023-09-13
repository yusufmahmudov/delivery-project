package food.delivery.repository;

import food.delivery.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    boolean existsByUsername(String username);


    Optional<Employee> findByUsername(String username);


    List<Employee> findAllByActive(Boolean active);


    @Modifying
    List<Employee> findAllByActiveAndWorkplace(Boolean active, String workplace);


    @Query("SELECT e.workplace, COUNT(e) FROM Employee e GROUP BY e.workplace")
    Map<String, Integer> countEmployeesByWorkplaceMap();

    boolean existsByPhoneNum1(String phone);

    Optional<Employee> findByPhoneNum1(String phone);


//    List<Employee> findAllByNameContainingIgnoreCaseAndActive(String name, boolean active);
}