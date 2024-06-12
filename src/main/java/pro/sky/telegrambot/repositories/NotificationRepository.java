package pro.sky.telegrambot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.NotificationTask;


import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationTask, Long> {
    List<NotificationTask> findNotificationTaskByTaskClock(LocalDateTime time);

    List<NotificationTask> findNotificationTaskByTaskReminderClock(LocalDateTime reminderClock);
}
