package com.portofolio.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DashboardStat {
	
	private String title;
	private String value;
	private String icon;
	private String change;
	private String color;
	
}
