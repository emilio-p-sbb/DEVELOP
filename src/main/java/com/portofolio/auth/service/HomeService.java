package com.portofolio.auth.service;

import com.portofolio.auth.dto.ActivityResponse;

public interface HomeService {

	ActivityResponse getActivities() throws Exception;
	
}
