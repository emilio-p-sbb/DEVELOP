package com.portofolio.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserInformation{
	private Long userId;
	private String fullname;
	private String email; 
	private String phone;
}
