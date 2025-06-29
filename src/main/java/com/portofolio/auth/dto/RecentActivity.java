package com.portofolio.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RecentActivity {

	private String action;
	private String item;
	private String time;
	private String icon;
	
}
