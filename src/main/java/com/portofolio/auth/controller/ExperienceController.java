package com.portofolio.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.portofolio.auth.dto.ExperienceRequest;
import com.portofolio.auth.dto.ExperienceResponse;
import com.portofolio.auth.dto.ResponseData;
import com.portofolio.auth.service.ExperienceService;

import java.util.List;

@RestController
@RequestMapping("/api/experiences")
@RequiredArgsConstructor
public class ExperienceController {

    private final ExperienceService experienceService;

    @GetMapping
    public ResponseEntity<ResponseData<List<ExperienceResponse>>> getAll() {
        return ResponseEntity.ok(
            ResponseData.<List<ExperienceResponse>>builder()
                .data(experienceService.getAll())
                .messages("Success")
                .code(HttpStatus.OK.value())
                .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<ExperienceResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
            ResponseData.<ExperienceResponse>builder()
                .data(experienceService.getById(id))
                .messages("Success")
                .code(HttpStatus.OK.value())
                .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseData<ExperienceResponse>> create(@Valid @RequestBody ExperienceRequest request) {
    	System.out.println("masuk save");
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseData.<ExperienceResponse>builder()
                .data(experienceService.create(request))
                .messages("Created successfully")
                .code(HttpStatus.CREATED.value())
                .build()
        );
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseData<ExperienceResponse>> update(
            @Valid @RequestBody ExperienceRequest request
    ) {
        return ResponseEntity.ok(
            ResponseData.<ExperienceResponse>builder()
                .data(experienceService.update(request))
                .messages("Updated successfully")
                .code(HttpStatus.OK.value())
                .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseData<Void>> delete(@PathVariable Long id) {
        experienceService.delete(id);
        return ResponseEntity.ok(
            ResponseData.<Void>builder()
                .data(null)
                .messages("Deleted successfully")
                .code(HttpStatus.OK.value())
                .build()
        );
    }
    
    @GetMapping("/public")
    public ResponseEntity<ResponseData<List<ExperienceResponse>>> getAllExperience() {
        return ResponseEntity.ok(
            ResponseData.<List<ExperienceResponse>>builder()
                .data(experienceService.getAll())
                .messages("Success")
                .code(HttpStatus.OK.value())
                .build()
        );
    }
}
