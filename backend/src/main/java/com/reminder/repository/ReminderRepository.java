package com.reminder.repository;

import com.reminder.model.Reminder;
import com.reminder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findByUserOrderByReminderTimeAsc(User user);
    Optional<Reminder> findByIdAndUser(Long id, User user);
}
