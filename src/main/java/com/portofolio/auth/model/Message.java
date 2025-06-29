package com.portofolio.auth.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "message")
public class Message extends BaseEntity {

    @Id
    @SequenceGenerator(name = "message_seq", sequenceName = "message_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_seq")
    @Column(name = "message_id", nullable = false)
    private Long messageId;

    @Column(name = "sender_name", nullable = false)
    private String from;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "message", columnDefinition = "TEXT", nullable = false)
    private String message;

    @Column(name = "time")
    private String time; // Bisa kamu ganti ke `LocalDateTime` jika ingin menyimpan waktu asli

    @Column(name = "is_read")
    private boolean isRead;

    @Column(name = "is_starred")
    private boolean isStarred;

    @Column(name = "is_archived")
    private boolean isArchived;
}
