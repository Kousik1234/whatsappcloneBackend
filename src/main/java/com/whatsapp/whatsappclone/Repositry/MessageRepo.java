package com.whatsapp.whatsappclone.Repositry;

import com.whatsapp.whatsappclone.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message , Long> {

    @Query("select m From Message m join m.chat c where c.id=:chatId")
    public List<Message> findByChatId(@Param("chatId") Long chatId);

}
