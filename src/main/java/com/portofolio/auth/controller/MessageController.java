package com.portofolio.auth.controller;

import com.portofolio.auth.dto.MessageRequest;
import com.portofolio.auth.dto.MessageResponse;
import com.portofolio.auth.dto.ResponseData;
import com.portofolio.auth.service.MessageService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired MessageService messageService;

    @GetMapping
    public ResponseEntity<ResponseData<List<MessageResponse>>> getAll() {
        return ResponseEntity.ok(
            ResponseData.<List<MessageResponse>>builder()
                .data(messageService.getAll())
                .messages("Success")
                .code(HttpStatus.OK.value())
                .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<MessageResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
            ResponseData.<MessageResponse>builder()
                .data(messageService.getById(id))
                .messages("Success")
                .code(HttpStatus.OK.value())
                .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseData<MessageResponse>> send(@Valid @RequestBody MessageRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseData.<MessageResponse>builder()
                .data(messageService.sendMessage(request))
                .messages("Message sent successfully")
                .code(HttpStatus.CREATED.value())
                .build()
        );
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<ResponseData<MessageResponse>> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(
            ResponseData.<MessageResponse>builder()
                .data(messageService.markAsRead(id))
                .messages("Marked as read")
                .code(HttpStatus.OK.value())
                .build()
        );
    }

    @PutMapping("/{id}/unread")
    public ResponseEntity<ResponseData<MessageResponse>> markAsUnread(@PathVariable Long id) {
        return ResponseEntity.ok(
            ResponseData.<MessageResponse>builder()
                .data(messageService.markAsUnread(id))
                .messages("Marked as unread")
                .code(HttpStatus.OK.value())
                .build()
        );
    }

    @PutMapping("/{id}/starred")
    public ResponseEntity<ResponseData<MessageResponse>> toggleStarred(@PathVariable Long id) {
        return ResponseEntity.ok(
            ResponseData.<MessageResponse>builder()
                .data(messageService.starred(id))
                .messages("Starred status updated")
                .code(HttpStatus.OK.value())
                .build()
        );
    }
    
    @PutMapping("/{id}/unstarred")
    public ResponseEntity<ResponseData<MessageResponse>> toggleUnStarred(@PathVariable Long id) {
    	return ResponseEntity.ok(
    			ResponseData.<MessageResponse>builder()
    			.data(messageService.unstarred(id))
    			.messages("Starred status updated")
    			.code(HttpStatus.OK.value())
    			.build()
    			);
    }

    @PutMapping("/{id}/archive")
    public ResponseEntity<ResponseData<MessageResponse>> archive(@PathVariable Long id) {
        return ResponseEntity.ok(
            ResponseData.<MessageResponse>builder()
                .data(messageService.archive(id))
                .messages("Archived")
                .code(HttpStatus.OK.value())
                .build()
        );
    }

    @PutMapping("/{id}/unarchive")
    public ResponseEntity<ResponseData<MessageResponse>> unarchive(@PathVariable Long id) {
        return ResponseEntity.ok(
            ResponseData.<MessageResponse>builder()
                .data(messageService.unarchive(id))
                .messages("Unarchived")
                .code(HttpStatus.OK.value())
                .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseData<Void>> delete(@PathVariable Long id) {
        messageService.delete(id);
        return ResponseEntity.ok(
            ResponseData.<Void>builder()
                .data(null)
                .messages("Deleted successfully")
                .code(HttpStatus.OK.value())
                .build()
        );
    }

    @PutMapping("/archive-all-read")
    public ResponseEntity<ResponseData<Void>> archiveAllRead() {
        messageService.archiveAllReadMessages();
        return ResponseEntity.ok(
            ResponseData.<Void>builder()
                .data(null)
                .messages("All read messages archived")
                .code(HttpStatus.OK.value())
                .build()
        );
    }

    @PutMapping("/mark-all-important")
    public ResponseEntity<ResponseData<Void>> markAllImportant() {
        messageService.markAllImportant();
        return ResponseEntity.ok(
            ResponseData.<Void>builder()
                .data(null)
                .messages("All messages marked as important")
                .code(HttpStatus.OK.value())
                .build()
        );
    }
}
