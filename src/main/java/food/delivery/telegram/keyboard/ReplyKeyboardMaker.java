package food.delivery.telegram.keyboard;

import food.delivery.model.User;
import food.delivery.repository.UserRepository;
import food.delivery.telegram.constants.BotMessageEnum;
import food.delivery.telegram.constants.ButtonCallbackEnum;
import food.delivery.telegram.constants.ButtonNameEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReplyKeyboardMaker {

    private final UserRepository userRepository;

    public ReplyKeyboardMarkup locationKeyboard() {

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> list = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setRequestLocation(true);
        keyboardButton.setText("\uD83D\uDCCD Location");

        row.add(keyboardButton);
        list.add(row);

        keyboardMarkup.setKeyboard(list);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        return keyboardMarkup;
    }


    public ReplyKeyboardMarkup contactKeyboard(Long chatId) {

        User user = userRepository.findById(chatId).get();
        String lan = user.getLanguageCode();

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> list = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setRequestContact(true);

        if (lan.equals(ButtonCallbackEnum.UZBEK_LANGUAGE.getCallback())) {
            keyboardButton.setText(ButtonNameEnum.CONTACT_BUTTON_NAME_UZ.getButtonName());
        } else if (lan.equals(ButtonCallbackEnum.ENGLISH_LANGUAGE.getCallback())) {
            keyboardButton.setText(ButtonNameEnum.CONTACT_BUTTON_NAME_EN.getButtonName());
        } else if (lan.equals(ButtonCallbackEnum.RUSSIAN_LANGUAGE.getCallback())) {
            keyboardButton.setText(ButtonNameEnum.CONTACT_BUTTON_NAME_RU.getButtonName());
        }

        row.add(keyboardButton);
        list.add(row);

        keyboardMarkup.setKeyboard(list);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        return keyboardMarkup;
    }

}
