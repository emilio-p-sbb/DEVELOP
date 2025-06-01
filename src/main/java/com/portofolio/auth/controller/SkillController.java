package com.portofolio.auth.controller;

import com.portofolio.auth.dto.*;
import com.portofolio.auth.service.SkillService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @GetMapping
    public ResponseEntity<ResponseData<List<SkillResponse>>> getAll() {
    	System.out.println("masuk get skills");
        return ResponseEntity.ok(
            ResponseData.<List<SkillResponse>>builder()
                .data(skillService.getAll())
                .messages("Success")
                .code(HttpStatus.OK.value())
                .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<SkillResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
            ResponseData.<SkillResponse>builder()
                .data(skillService.getById(id))
                .messages("Success")
                .code(HttpStatus.OK.value())
                .build()
        );
    }

    @PostMapping
    public ResponseEntity<ResponseData<SkillResponse>> create(@Valid @RequestBody SkillRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseData.<SkillResponse>builder()
                .data(skillService.create(request))
                .messages("Created successfully")
                .code(HttpStatus.CREATED.value())
                .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<SkillResponse>> update(@PathVariable Long id, @Valid @RequestBody SkillRequest request) {
        return ResponseEntity.ok(
            ResponseData.<SkillResponse>builder()
                .data(skillService.update(id, request))
                .messages("Updated successfully")
                .code(HttpStatus.OK.value())
                .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<Void>> delete(@PathVariable Long id) {
        skillService.delete(id);
        return ResponseEntity.ok(
            ResponseData.<Void>builder()
                .data(null)
                .messages("Deleted successfully")
                .code(HttpStatus.OK.value())
                .build()
        );
    }
}