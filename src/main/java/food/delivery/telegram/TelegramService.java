package food.delivery.telegram;

import food.delivery.dto.UserDto;
import food.delivery.dto.response.GetResponse;
import food.delivery.helper.AppMessages;
import food.delivery.model.User;
import food.delivery.repository.UserRepository;
import food.delivery.service.mapper.interfaces.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Value("${main.domain}")
    private String domain;


    public ResponseEntity<?> addAdminForTg(Long userId, Boolean admin) {
        User user = userRepository.findById(userId).get();
        user.setIsAdmin(admin);

        userRepository.save(user);

        return ResponseEntity.accepted().body(AppMessages.SAVED);
    }


    public ResponseEntity<?> allAdmin(Integer limit, Integer offset) {
        List<UserDto> users = userRepository.findAllByIsAdmin(true)
                .stream().map(userMapper::toDto).toList();
        List<UserDto> result = new ArrayList<>();

        GetResponse response = new GetResponse();
        response.setCount(0);
        response.setPrevious(domain + "/employee/get-all-admin/?limit="
                +limit+"&offset=0");
        response.setData(result);

        if (users.size() <= offset) {
            return ResponseEntity.ok().body(response);
        }

        for (int i = offset; i < offset+limit; i++) {
            result.add(users.get(i));
            if (users.size()-1 == i) break;
        }

        response.setCount(result.size());
        response.setData(result);
        response.setNext(users.size() >= offset+limit?domain + "/employee/get-all-admin/?limit="+limit
                +"&offset="+(offset+limit):null);
        response.setPrevious(domain + "/employee/get-all-admin/?limit="+limit+"&offset=" + (Math.max(offset-limit, 0)));

        return ResponseEntity.ok().body(response);
    }

}
