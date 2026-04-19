package com.reminder.controller;

import com.reminder.model.Reminder;
import com.reminder.model.User;
import com.reminder.repository.ReminderRepository;
import com.reminder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow();
    }

    @GetMapping
    public ResponseEntity<?> getAllReminders() {
        User user = getCurrentUser();
        List<Reminder> reminders = reminderRepository.findByUserOrderByReminderTimeAsc(user);
        return ResponseEntity.ok(reminders.stream().map(this::toMap).toList());
    }

    @PostMapping
    public ResponseEntity<?> createReminder(@RequestBody Map<String, String> body) {
        User user = getCurrentUser();

        Reminder reminder = new Reminder();
        reminder.setTitle(body.get("title"));
        reminder.setDescription(body.get("description"));
        reminder.setReminderTime(LocalDateTime.parse(body.get("reminderTime")));
        reminder.setPriority(body.getOrDefault("priority", "MEDIUM"));
        reminder.setStatus("PENDING");
        reminder.setUser(user);

        reminderRepository.save(reminder);
        return ResponseEntity.ok(toMap(reminder));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReminder(@PathVariable Long id,
                                             @RequestBody Map<String, String> body) {
        User user = getCurrentUser();
        Optional<Reminder> opt = reminderRepository.findByIdAndUser(id, user);
        if (opt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Reminder not found"));
        }

        Reminder reminder = opt.get();
        if (body.containsKey("title"))        reminder.setTitle(body.get("title"));
        if (body.containsKey("description"))  reminder.setDescription(body.get("description"));
        if (body.containsKey("reminderTime")) reminder.setReminderTime(LocalDateTime.parse(body.get("reminderTime")));
        if (body.containsKey("priority"))     reminder.setPriority(body.get("priority"));
        if (body.containsKey("status"))       reminder.setStatus(body.get("status"));

        reminderRepository.save(reminder);
        return ResponseEntity.ok(toMap(reminder));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReminder(@PathVariable Long id) {
        User user = getCurrentUser();
        Optional<Reminder> opt = reminderRepository.findByIdAndUser(id, user);
        if (opt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Reminder not found"));
        }
        reminderRepository.delete(opt.get());
        return ResponseEntity.ok(Map.of("message", "Deleted successfully"));
    }

    private Map<String, Object> toMap(Reminder r) {
        return Map.of(
            "id", r.getId(),
            "title", r.getTitle(),
            "description", r.getDescription() != null ? r.getDescription() : "",
            "reminderTime", r.getReminderTime().toString(),
            "priority", r.getPriority(),
            "status", r.getStatus(),
            "createdAt", r.getCreatedAt().toString()
        );
    }
}
