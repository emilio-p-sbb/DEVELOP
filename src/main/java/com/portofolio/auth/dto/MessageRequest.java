package com.portofolio.auth.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {

    private Long id;

    @NotBlank
    private String from;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String subject;

    @NotBlank
    private String message;
    
    private boolean read;
    private boolean starred;
    private boolean archived;
    
}
