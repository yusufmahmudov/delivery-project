package food.delivery.repository;

import food.delivery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByPhoneNum1(String phone);

    Long countByCreatedAtBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<User> findAllByIsActive(Boolean active);

    Optional<User> findByPhoneNum1(String phone);

    boolean existsByTgId(Long tgId);

    Optional<User> findByTgId(Long tgId);

    List<User> findAllByIsAdmin(boolean isAdmin);
}