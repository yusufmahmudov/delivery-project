package food.delivery.telegram.keyboard;

import food.delivery.telegram.constants.ButtonCallbackEnum;
import food.delivery.telegram.constants.ButtonNameEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InlineKeyboardMaker {


    public InlineKeyboardMarkup languageKeyboard() {

        InlineKeyboardMarkup inline = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> list1 = new ArrayList<>();
        List<InlineKeyboardButton> list2 = new ArrayList<>();
        List<InlineKeyboardButton> list3 = new ArrayList<>();

        InlineKeyboardButton Uz = new InlineKeyboardButton();
        Uz.setText(ButtonNameEnum.LANGUAGE_BUTTON_NAME_UZ.getButtonName());
        Uz.setCallbackData(ButtonCallbackEnum.UZBEK_LANGUAGE.getCallback());
        list1.add(Uz);

        InlineKeyboardButton En = new InlineKeyboardButton();
        En.setText(ButtonNameEnum.LANGUAGE_BUTTON_NAME_EN.getButtonName());
        En.setCallbackData(ButtonCallbackEnum.ENGLISH_LANGUAGE.getCallback());
        list2.add(En);

        InlineKeyboardButton Ru = new InlineKeyboardButton();
        Ru.setText(ButtonNameEnum.LANGUAGE_BUTTON_NAME_RU.getButtonName());
        Ru.setCallbackData(ButtonCallbackEnum.RUSSIAN_LANGUAGE.getCallback());
        list3.add(Ru);

        List<List<InlineKeyboardButton>> lists = new ArrayList<>();
        lists.add(list1);
        lists.add(list2);
        lists.add(list3);
        inline.setKeyboard(lists);

        return inline;
    }


    public InlineKeyboardMarkup mainMenu(Long chatId, String lan) {

        InlineKeyboardMarkup inline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> lists = new ArrayList<>();
        List<InlineKeyboardButton> list = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton();

        if (lan.equals(ButtonCallbackEnum.UZBEK_LANGUAGE.getCallback())) {
            button.setText(ButtonNameEnum.WEBAPP_BUTTON_NAME_UZ.getButtonName());
        } else if (lan.equals(ButtonCallbackEnum.ENGLISH_LANGUAGE.getCallback())) {
            button.setText(ButtonNameEnum.WEBAPP_BUTTON_NAME_EN.getButtonName());
        } else if (lan.equals(ButtonCallbackEnum.RUSSIAN_LANGUAGE.getCallback())) {
            button.setText(ButtonNameEnum.WEBAPP_BUTTON_NAME_RU.getButtonName());
        }

        WebAppInfo webAppInfo = new WebAppInfo();
        webAppInfo.setUrl("https://google.com");
//        webAppInfo.setUrl("http://localhost:8080/api/auth/login?chatId=" + chatId);

        button.setWebApp(webAppInfo);
        list.add(button);

        lists.add(list);
        inline.setKeyboard(lists);

        return inline;
    }

}
