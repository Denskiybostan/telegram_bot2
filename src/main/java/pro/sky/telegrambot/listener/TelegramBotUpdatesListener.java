package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repositories.NotificationRepository;


import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);


    private static String MESSAGE_WELCOME = "Привет,введи по порядку время и название задачи в формате: 01.01.2022 20:00 сделать ДЗ";
    private static Pattern PATTERN = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\w+]+)");
    private static DateTimeFormatter DATE_TIME_FORMATER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private TelegramBot telegramBot;
    private final NotificationRepository notificationRepository;

    public TelegramBotUpdatesListener(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public TelegramBotUpdatesListener(TelegramBot telegramBot, NotificationRepository notificationRepository) {
        this.telegramBot = telegramBot;
        this.notificationRepository = notificationRepository;
    }


    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update", update);
            var message = update.message();
            if (message != null) {
                var messageText = update.message().text();
                var chatId = update.message().chat().id();
                if (messageText != null && messageText.equals("/start")) {
                    sendMessage(chatId, MESSAGE_WELCOME);
                } else {
                    splitMessage(chatId, messageText);
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void splitMessage(Long chat, String message) {
        Matcher matcher = PATTERN.matcher(message);
        try {
            if (!matcher.matches()) {
                throw new IllegalStateException("Please try again");
            }
        } catch (IllegalStateException e) {
            logger.error("invalid format", matcher, message);
            sendMessage(chat, "Недопустимые символы");
            return;
        }
        var dateTime = matcher.group(1);
        var reminderText = matcher.group(3);
        LocalDateTime reminderClock;
        try {
            reminderClock = LocalDateTime.parse(dateTime, DATE_TIME_FORMATER);
        } catch (DateTimeParseException e) {
            logger.error("invalid format", dateTime, DATE_TIME_FORMATER);
            sendMessage(chat, "неправильный формат даты");
            return;
        }
        NotificationTask task = new NotificationTask();
        task.setChatId(chat);
        task.setTaskText(reminderText);

    }
    private void sendMessage(Long chat, String text) {
        SendMessage message = new SendMessage(chat, text);
    }


}
