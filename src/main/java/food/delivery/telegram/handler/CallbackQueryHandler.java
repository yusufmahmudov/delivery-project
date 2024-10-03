package food.delivery.telegram.handler;

import food.delivery.model.User;
import food.delivery.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;

@Component
@RequiredArgsConstructor
public class CallbackQueryHandler {

    private final UserRepository userRepository;


    public DeleteMessage languageCorrection(Long chatId, String data, Integer messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(messageId);
        User user = userRepository.findByTgId(chatId).get();
        user.setLanguageCode(data);
        user.setIsActive(true);
        userRepository.save(user);

        return deleteMessage;
    }


}