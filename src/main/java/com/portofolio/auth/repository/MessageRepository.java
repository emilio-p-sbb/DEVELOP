package com.portofolio.auth.repository;

import com.portofolio.auth.model.Message;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    long countByIsReadFalse(); // Hitung pesan yang belum dibaca

    long countByIsArchivedTrue(); // Hitung pesan yang sudah diarsipkan
    
    List<Message> findTop3ByOrderByUpdatedAtDesc();


}
