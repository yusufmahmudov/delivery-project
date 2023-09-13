package food.delivery.telegram;

import food.delivery.helper.AppMessages;
import food.delivery.model.User;
import food.delivery.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramService {

    private final UserRepository userRepository;


    public ResponseEntity<?> addAdminForTg(Long userId, Boolean admin) {
        User user = userRepository.findById(userId).get();
        user.setIsAdmin(admin);

        userRepository.save(user);

        return ResponseEntity.accepted().body(AppMessages.SAVED);
    }


    public ResponseEntity<?> allAdmin() {
        List<User> users = userRepository.findAllByIsAdmin(true);

        return ResponseEntity.ok().body(users);
    }


}
