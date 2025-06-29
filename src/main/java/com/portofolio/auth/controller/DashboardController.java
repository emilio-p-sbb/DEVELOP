package com.portofolio.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portofolio.auth.dto.ActivityResponse;
import com.portofolio.auth.dto.ResponseData;
import com.portofolio.auth.service.HomeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class DashboardController {
	
	@Autowired HomeService homeService;

	@GetMapping("/activities/recent")
    public ResponseEntity<ResponseData<ActivityResponse>> getAllProject() throws Exception {
		
        return ResponseEntity.ok(
            ResponseData.<ActivityResponse>builder()
                .data(homeService.getActivities())
                .messages("Success")
                .code(HttpStatus.OK.value())
                .build()
        );
    }
}
