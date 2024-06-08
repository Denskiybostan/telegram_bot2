package pro.sky.telegrambot.notification;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.repositories.NotificationRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

@Service
public class NotificationTaskScheduler {
    private static Logger logger = (Logger) LoggerFactory.getLogger(NotificationTaskScheduler.class);
    private TelegramBot telegramBot;
    private NotificationRepository repository;
    public NotificationTaskScheduler (TelegramBot telegramBot, NotificationRepository repository) {
        this.telegramBot = telegramBot;
        this.repository = repository;
    }

    @Scheduled(cron = "0 0/1 * * * * ")
    public void sendAndDeleteNotification() {
        repository.findNotificationTaskByTaskClock(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                .forEach(task ->{
                    telegramBot.execute(new SendMessage(task.getChatId(), task.getTaskText()));
                    logger.info("Message has been send");
                    repository.deleteById(task.getId());

        });
    }
}
