package com.portofolio.auth.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.portofolio.auth.dto.ResponseData;
import com.portofolio.auth.dto.SkillByCategoryResponse;
import com.portofolio.auth.dto.SkillRequest;
import com.portofolio.auth.dto.SkillResponse;
import com.portofolio.auth.service.SkillService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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
    
    @GetMapping("/findBy")
    public ResponseEntity<ResponseData<List<SkillResponse>>> findByKeyword(@RequestParam String keyword) throws Exception {
        System.out.println("masuk get skills by keyword");
        return ResponseEntity.ok(
            ResponseData.<List<SkillResponse>>builder()
                .data(skillService.findBySearch(keyword))
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

    @PostMapping("/save")
    public ResponseEntity<ResponseData<SkillResponse>> create(@Valid @RequestBody SkillRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseData.<SkillResponse>builder()
                .data(skillService.create(request))
                .messages("Created successfully")
                .code(HttpStatus.CREATED.value())
                .build()
        );
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseData<SkillResponse>> update(@Valid @RequestBody SkillRequest request) {
        return ResponseEntity.ok(
            ResponseData.<SkillResponse>builder()
                .data(skillService.update(request))
                .messages("Updated successfully")
                .code(HttpStatus.OK.value())
                .build()
        );
    }

    @DeleteMapping("/delete/{id}")
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
    
    @GetMapping("/public")
    public ResponseEntity<ResponseData<List<SkillByCategoryResponse>>> getAllSkill() {
        System.out.println("masuk get skills public");
        return ResponseEntity.ok(
            ResponseData.<List<SkillByCategoryResponse>>builder()
                .data(skillService.getAllSkillByCategory())
                .messages("Success")
                .code(HttpStatus.OK.value())
                .build()
        );
    }

}