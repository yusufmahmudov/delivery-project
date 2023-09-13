package food.delivery.telegram;

import food.delivery.model.User;
import food.delivery.repository.UserRepository;
import food.delivery.telegram.config.BotConfig;
import food.delivery.telegram.constants.BotMessageEnum;
import food.delivery.telegram.handler.CallbackQueryHandler;
import food.delivery.telegram.handler.MessageHandler;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private BotConfig botConfig;
    @Autowired
    private CallbackQueryHandler callbackQueryHandler;
    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    private UserRepository userRepository;

    private List<User> owners;


    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        owners = userRepository.findAllByIsAdmin(true)
                .stream().toList();
        return botConfig.getToken();
    }


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && isAdmin(update.getMessage().getChatId())) {
                Message message = update.getMessage();
                if (message.getText() == null || !message.getText().equals("/start")) {
                    sendMessageAllUser(message);
                } else {
                    User user = userRepository.findByTgId(message.getChatId()).get();
                    deleteMessage(message.getMessageId(), message.getChatId());
                    execute(messageHandler.mainMenu(message.getChatId(), user.getLanguageCode()));
                }
            } else {
                handleUpdate(update);
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    private void handleUpdate(Update update) throws TelegramApiException {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            Long chatId = callbackQuery.getMessage().getChatId();
            Integer messageId = callbackQuery.getMessage().getMessageId();
            String data = callbackQuery.getData();

            if (data.contains("language")) {
                execute(callbackQueryHandler.languageCorrection(chatId, data, messageId));
                execute(messageHandler.mainMenu(chatId, data));
            }
        } else {
            Long chatId = update.getMessage().getChatId();
            Message message = update.getMessage();
            String messageText = message.getText();

            if (!userRepository.existsByTgId(chatId)) {
                sendMessageNewUser(chatId, message);
            }

            User user = userRepository.findByTgId(chatId).get();
            String lan = user.getLanguageCode();

            if (update.hasMessage() && update.getMessage().hasText()
                    && messageText.equals("/start"))
            {
                if (StringUtils.isEmpty(lan)) {
                    execute(messageHandler.getLanguageMessage(chatId));
                } else {
                    deleteMessage(message.getMessageId(), chatId);
                    execute(messageHandler.mainMenu(message.getChatId(), lan));
                }
            } else {
                deleteMessage(message.getMessageId(), chatId);
            }
        }
    }


    private void deleteMessage(Integer messageId, Long chatId) throws TelegramApiException {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setMessageId(messageId);
        deleteMessage.setChatId(chatId);
        execute(deleteMessage);
    }


    private void sendMessageNewUser(Long chatId, Message message) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(BotMessageEnum.FIRST_MESSAGE.getMessage());
        messageHandler.registerUser(message);
        execute(sendMessage);
    }


    private void sendMessageAllUser(Message message) throws TelegramApiException, InterruptedException {
        List<User> users = new java.util.ArrayList<>(userRepository.findAllByIsActive(true)
                .stream().toList());

        String text = message.getText();
        users.removeIf(u -> Objects.equals(u.getTgId(), message.getChatId()));

        if (text != null && text.startsWith("/reklama")) {
            text = text.substring(9);
            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            sendMessage.setText(text);
            sendMessage.setParseMode(ParseMode.MARKDOWNV2);

            for (User u : users) {
                sendMessage.setChatId(u.getTgId());
                execute(sendMessage);
                execute(messageHandler.mainMenu(u.getTgId(), u.getLanguageCode()));
                Thread.sleep(50);
            }
        } else if (message.getCaption() == null) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("Kalit so'zni kiriting. Aks holda xabaringiz yuborilmaydi...");
            sendMessage.setChatId(message.getChatId());
            execute(sendMessage);
//            deleteMessage(message.getMessageId(), message.getChatId());
        } else if (message.hasPhoto() && message.getCaption().startsWith("/reklama")) {
            SendPhoto sendPhoto = new SendPhoto();
            if (message.getCaption().length() > 8) {
                String caption = message.getCaption().substring(9);
                sendPhoto.setCaption(caption);
            }
            List<PhotoSize> photos = message.getPhoto();

            String fileId = Objects.requireNonNull(photos.stream()
                    .max(Comparator.comparing(PhotoSize::getFileSize))
                    .orElse(null)).getFileId();

            sendPhoto.setPhoto(new InputFile(fileId));
            sendPhoto.setParseMode(ParseMode.MARKDOWNV2);

            for (User u : users) {
                sendPhoto.setChatId(u.getTgId());
                execute(sendPhoto);
                execute(messageHandler.mainMenu(u.getTgId(), u.getLanguageCode()));
                Thread.sleep(50);
            }
        } else if (message.hasVideo() && message.getCaption().startsWith("/reklama")) {
            SendVideo sendVideo = new SendVideo();
            if (message.getCaption().length() > 8) {
                String caption = message.getCaption().substring(9);
                sendVideo.setCaption(caption);
            }
            Video video = message.getVideo();

            String fileId = video.getFileId();

            sendVideo.setVideo(new InputFile(fileId));
            sendVideo.setParseMode(ParseMode.MARKDOWNV2);

            for (User u : users) {
                sendVideo.setChatId(u.getTgId());
                execute(sendVideo);
                execute(messageHandler.mainMenu(u.getTgId(), u.getLanguageCode()));
                Thread.sleep(50);
            }
        } else {
            deleteMessage(message.getMessageId(), message.getChatId());
        }
    }


    private boolean isAdmin(Long chatId) {
        for (User o : owners) {
            if (Objects.equals(o.getTgId(), chatId)) {
                return true;
            }
        }
        return false;
    }


}
