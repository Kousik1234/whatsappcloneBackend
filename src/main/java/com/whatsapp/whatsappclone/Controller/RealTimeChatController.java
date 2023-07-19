package com.whatsapp.whatsappclone.Controller;

import com.whatsapp.whatsappclone.Entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RealTimeChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/group/public")
    public Message reciveMessage(@Payload Message message) {
        simpMessagingTemplate.convertAndSend("/group/"+message.getChat().getId().toString(),message);
        return message;
    }

}
