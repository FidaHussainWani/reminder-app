package com.reminder.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "reminders")
@Data
@NoArgsConstructor
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "reminder_time", nullable = false)
    private LocalDateTime reminderTime;

    @Column(nullable = false)
    private String priority = "MEDIUM"; // LOW, MEDIUM, HIGH

    @Column(nullable = false)
    private String status = "PENDING"; // PENDING, DONE

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
