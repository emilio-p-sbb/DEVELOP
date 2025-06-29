package com.portofolio.auth.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.portofolio.auth.enums.TokenType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tokens")
public class Token {
	
	@Id
	@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "token_seq", sequenceName = "token_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_seq")
	@Column(name = "token_id", nullable = false)
	private long tokenId;
    private TokenType type;
    private String value;
    private LocalDateTime expiryDate;
    private boolean disabled;
    @ManyToOne
    private User user;
    
}