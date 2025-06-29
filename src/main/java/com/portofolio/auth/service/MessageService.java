package com.portofolio.auth.service;

import java.util.List;

import com.portofolio.auth.dto.MessageRequest;
import com.portofolio.auth.dto.MessageResponse;

public interface MessageService {
	
	List<MessageResponse> getAll();
    MessageResponse getById(Long id);
    List<MessageResponse> findBySearch(String keyword);
    MessageResponse create(MessageRequest request);
    MessageResponse update(MessageRequest request);
    void delete(Long id);

    // Opsional tambahan untuk fitur toggling
    MessageResponse markAsRead(Long id);
    MessageResponse markAsUnread(Long id);
//    MessageResponse toggleStarred(Long id, boolean isStarred);
//    MessageResponse toggleUnstarred(Long id, boolean isStarred);
    MessageResponse archive(Long id);
    MessageResponse unarchive(Long id);
    
    MessageResponse starred(Long id);
    MessageResponse unstarred(Long id);
    
    MessageResponse sendMessage(MessageRequest request);

    void archiveAllReadMessages();
    void markAllImportant();

}
