package com.whatsapp.whatsappclone.Repositry;

import com.whatsapp.whatsappclone.Entity.Chat;
import com.whatsapp.whatsappclone.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepo extends JpaRepository<Chat , Long> {

    @Query("select c from Chat c join c.users u where u.id=:userId")
    public List<Chat> findChatByUserId(@Param("userId") Long userId);

    @Query("select c from Chat c where c.isGroup=false And :recUser Member of c.users And :reqUser Member of c.users")
    public Chat findSingleChatByUserIds(@Param("recUser")User recUser , @Param("reqUser")User reqUser);

}
