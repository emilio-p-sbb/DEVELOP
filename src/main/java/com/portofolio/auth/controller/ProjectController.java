package com.portofolio.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portofolio.auth.dto.ProjectRequest;
import com.portofolio.auth.dto.ProjectResponse;
import com.portofolio.auth.dto.ResponseData;
import com.portofolio.auth.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<ResponseData<List<ProjectResponse>>> getAll() {
        return ResponseEntity.ok(
            ResponseData.<List<ProjectResponse>>builder()
                .data(projectService.getAll())
                .messages("Success")
                .code(HttpStatus.OK.value())
                .build()
        );
    }

    @GetMapping("/findBy")
    public ResponseEntity<ResponseData<List<ProjectResponse>>> findByKeyword(@RequestParam String keyword) throws Exception {
        return ResponseEntity.ok(
            ResponseData.<List<ProjectResponse>>builder()
                .data(projectService.findBySearch(keyword))
                .messages("Success")
                .code(HttpStatus.OK.value())
                .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<ProjectResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
            ResponseData.<ProjectResponse>builder()
                .data(projectService.getById(id))
                .messages("Success")
                .code(HttpStatus.OK.value())
                .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseData<ProjectResponse>> create(@Valid @RequestBody ProjectRequest request) throws Exception {
        System.out.println("masuk save project");
        System.out.println("request = " + new ObjectMapper().writeValueAsString(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseData.<ProjectResponse>builder()
                .data(projectService.create(request))
                .messages("Created successfully")
                .code(HttpStatus.CREATED.value())
                .build()
        );
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseData<ProjectResponse>> update(@Valid @RequestBody ProjectRequest request) {
        return ResponseEntity.ok(
            ResponseData.<ProjectResponse>builder()
                .data(projectService.update(request))
                .messages("Updated successfully")
                .code(HttpStatus.OK.value())
                .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseData<Void>> delete(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.ok(
            ResponseData.<Void>builder()
                .data(null)
                .messages("Deleted successfully")
                .code(HttpStatus.OK.value())
                .build()
        );
    }
    
    @GetMapping("/public")
    public ResponseEntity<ResponseData<List<ProjectResponse>>> getAllProject() {
        return ResponseEntity.ok(
            ResponseData.<List<ProjectResponse>>builder()
                .data(projectService.getAll())
                .messages("Success")
                .code(HttpStatus.OK.value())
                .build()
        );
    }
}
