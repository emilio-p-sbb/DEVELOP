package com.portofolio.auth.controller;

import com.portofolio.auth.dto.EducationRequest;
import com.portofolio.auth.dto.EducationResponse;
import com.portofolio.auth.dto.ResponseData;
import com.portofolio.auth.service.EducationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/educations")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService educationService;

    @GetMapping
    public ResponseEntity<ResponseData<List<EducationResponse>>> getAll() {
        return ResponseEntity.ok(
            ResponseData.<List<EducationResponse>>builder()
                .data(educationService.getAll())
                .messages("Success")
                .code(HttpStatus.OK.value())
                .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<EducationResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
            ResponseData.<EducationResponse>builder()
                .data(educationService.getById(id))
                .messages("Success")
                .code(HttpStatus.OK.value())
                .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseData<EducationResponse>> create(@Valid @RequestBody EducationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseData.<EducationResponse>builder()
                .data(educationService.create(request))
                .messages("Created successfully")
                .code(HttpStatus.CREATED.value())
                .build()
        );
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseData<EducationResponse>> update(@Valid @RequestBody EducationRequest request) {
        return ResponseEntity.ok(
            ResponseData.<EducationResponse>builder()
                .data(educationService.update(request))
                .messages("Updated successfully")
                .code(HttpStatus.OK.value())
                .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseData<Void>> delete(@PathVariable Long id) {
        educationService.delete(id);
        return ResponseEntity.ok(
            ResponseData.<Void>builder()
                .data(null)
                .messages("Deleted successfully")
                .code(HttpStatus.OK.value())
                .build()
        );
    }

    @GetMapping("/public")
    public ResponseEntity<ResponseData<List<EducationResponse>>> getAllPublic() {
        return ResponseEntity.ok(
            ResponseData.<List<EducationResponse>>builder()
                .data(educationService.getAll())
                .messages("Success")
                .code(HttpStatus.OK.value())
                .build()
        );
    }
}
