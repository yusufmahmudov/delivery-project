package food.delivery.telegram.handler;

import food.delivery.model.Role;
import food.delivery.model.User;
import food.delivery.repository.RoleRepository;
import food.delivery.repository.UserRepository;
import food.delivery.telegram.constants.BotMessageEnum;
import food.delivery.telegram.constants.ButtonCallbackEnum;
import food.delivery.telegram.keyboard.InlineKeyboardMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Component
@RequiredArgsConstructor
public class MessageHandler {

    private final UserRepository userRepository;
    private final InlineKeyboardMaker inlineKeyboardMaker;
    private final RoleRepository roleRepository;


    public SendMessage mainMenu(Long chatId, String languageCode) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        if (languageCode.equals(ButtonCallbackEnum.UZBEK_LANGUAGE.getCallback())) {
            sendMessage.setText(BotMessageEnum.WEBAPP_MESSAGE_UZ.getMessage());
        } else if (languageCode.equals(ButtonCallbackEnum.ENGLISH_LANGUAGE.getCallback())) {
            sendMessage.setText(BotMessageEnum.WEBAPP_MESSAGE_EN.getMessage());
        } else if (languageCode.equals(ButtonCallbackEnum.RUSSIAN_LANGUAGE.getCallback())) {
            sendMessage.setText(BotMessageEnum.WEBAPP_MESSAGE_RU.getMessage());
        }

        sendMessage.setReplyMarkup(inlineKeyboardMaker.mainMenu(chatId, languageCode));
        sendMessage.setParseMode(ParseMode.MARKDOWN);

        return sendMessage;
    }


    public SendMessage getLanguageMessage(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Tilni tanlang\n\nSelect a language\n\nВыберите язык");
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(inlineKeyboardMaker.languageKeyboard());
        sendMessage.setDisableNotification(true);

        return sendMessage;
    }


    public void registerUser(Message message) {
        long chatId = message.getChatId();
        String name = message.getFrom().getFirstName();
        String last = message.getFrom().getLastName();
        if (last != null) name += " " + last;

        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName("ROLE_USER").get();
        roles.add(role);

        User user = new User();
        user.setTgId(chatId);
        user.setFullName(name);
        user.setTgUsername(message.getFrom().getUserName());
        user.setCreatedAt(LocalDateTime.now());
        user.setIsActive(false);
        user.setIsAdmin(false);
        user.setRoles(roles);

        userRepository.save(user);
    }

}