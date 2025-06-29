package com.portofolio.auth.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {
    private Long id;
    private String from;
    private String email;
    private String subject;
    private String message;
    private boolean read;
    private boolean starred;
    private boolean archived;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
