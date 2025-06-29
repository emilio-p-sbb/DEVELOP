package com.portofolio.auth.service;

import com.portofolio.auth.dto.MessageRequest;
import com.portofolio.auth.dto.MessageResponse;
import com.portofolio.auth.exception.ResourceNotFoundException;
import com.portofolio.auth.model.Message;
import com.portofolio.auth.repository.MessageRepository;
import com.portofolio.auth.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public List<MessageResponse> getAll() {
        return messageRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MessageResponse getById(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found"));
        return toResponse(message);
    }

    @Override
    public List<MessageResponse> findBySearch(String keyword) {
        return messageRepository.findAll().stream()
                .filter(m -> m.getSubject().toLowerCase().contains(keyword.toLowerCase()) ||
                             m.getMessage().toLowerCase().contains(keyword.toLowerCase()) ||
                             m.getFrom().toLowerCase().contains(keyword.toLowerCase()))
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MessageResponse create(MessageRequest request) {
        Message message = toEntity(request);
        message.setMessageId(null); // Ensure it's treated as new
        return toResponse(messageRepository.save(message));
    }

    @Override
    public MessageResponse update(MessageRequest request) {
        Message existing = messageRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Message not found"));
        existing.setFrom(request.getFrom());
        existing.setEmail(request.getEmail());
        existing.setSubject(request.getSubject());
        existing.setMessage(request.getMessage());
        existing.setRead(request.isRead());
        existing.setStarred(request.isStarred());
        existing.setArchived(request.isArchived());
        return toResponse(messageRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found"));
        messageRepository.delete(message);
    }
    
    @Override
    public MessageResponse sendMessage(MessageRequest request) {
        Message message = Message.builder()
            .from(request.getFrom())
            .email(request.getEmail())
            .subject(request.getSubject())
            .message(request.getMessage())
            .isRead(false)
            .isStarred(false)
            .isArchived(false)
            .build();
        return toResponse(messageRepository.save(message));
    }

    @Override
    public void archiveAllReadMessages() {
        List<Message> messages = messageRepository.findAll();
        for (Message msg : messages) {
            if (msg.isRead() && !msg.isArchived()) {
                msg.setArchived(true);
            }
        }
        messageRepository.saveAll(messages);
    }

    @Override
    public void markAllImportant() {
        List<Message> messages = messageRepository.findAll();
        for (Message msg : messages) {
            msg.setStarred(true);
        }
        messageRepository.saveAll(messages);
    }

    @Override
    public MessageResponse markAsRead(Long id) {
        return toggleField(id, m -> m.setRead(true));
    }

    @Override
    public MessageResponse markAsUnread(Long id) {
        return toggleField(id, m -> m.setRead(false));
    }

    @Override
    public MessageResponse archive(Long id) {
        return toggleField(id, m -> m.setArchived(true));
    }

    @Override
    public MessageResponse unarchive(Long id) {
        return toggleField(id, m -> m.setArchived(false));
    }
    
    @Override
    public MessageResponse starred(Long id) {
        return toggleField(id, m -> m.setStarred(true));
    }

    @Override
    public MessageResponse unstarred(Long id) {
        return toggleField(id, m -> m.setStarred(false));
    }


    private MessageResponse toggleField(Long id, java.util.function.Consumer<Message> updater) {
    	System.out.println("updater = "+updater);
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found"));
        updater.accept(message);
        return toResponse(messageRepository.save(message));
    }

    private MessageResponse toResponse(Message message) {
        return MessageResponse.builder()
                .id(message.getMessageId())
                .from(message.getFrom())
                .email(message.getEmail())
                .subject(message.getSubject())
                .message(message.getMessage())
                .read(message.isRead())
                .starred(message.isStarred())
                .archived(message.isArchived())
                .createdAt(message.getCreatedAt())
                .updatedAt(message.getUpdatedAt())
                .build();
    }

    private Message toEntity(MessageRequest request) {
        return Message.builder()
                .messageId(request.getId())
                .from(request.getFrom())
                .email(request.getEmail())
                .subject(request.getSubject())
                .message(request.getMessage())
                .isRead(request.isRead())
                .isStarred(request.isStarred())
                .isArchived(request.isArchived())
                .build();
    }
}