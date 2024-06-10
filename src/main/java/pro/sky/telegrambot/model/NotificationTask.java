package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "notification_task")
public class NotificationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long chatId;
    private String taskText;
    private LocalDateTime taskClock;
    private LocalDateTime reminderClock;

    public NotificationTask() {

    }

    public LocalDateTime getReminderClock() {
        return reminderClock;
    }

    public void setReminderClock(LocalDateTime reminderClock) {
        this.reminderClock = reminderClock;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getTaskClock() {
        return taskClock;
    }

    public void setTaskClock(LocalDateTime taskClock) {
        this.taskClock = taskClock;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask task = (NotificationTask) o;
        return id == task.id && chatId == task.chatId && Objects.equals(taskText, task.taskText) && Objects.equals(taskClock, task.taskClock) && Objects.equals(reminderClock, task.reminderClock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, taskText, taskClock, reminderClock);
    }

    @Override
    public String toString() {
        return "NotificationTask{" +
                "chatId=" + chatId +
                ", id=" + id +
                ", taskText='" + taskText + '\'' +
                ", taskClock=" + taskClock +
                ", remiderClock=" + reminderClock +
                '}';
    }
}
