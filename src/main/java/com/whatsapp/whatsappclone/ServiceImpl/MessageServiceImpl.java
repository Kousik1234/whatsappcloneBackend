package com.whatsapp.whatsappclone.ServiceImpl;

import com.whatsapp.whatsappclone.DTO.SendMessageDto;
import com.whatsapp.whatsappclone.Entity.Chat;
import com.whatsapp.whatsappclone.Entity.Message;
import com.whatsapp.whatsappclone.Entity.User;
import com.whatsapp.whatsappclone.Exception.ChatException;
import com.whatsapp.whatsappclone.Exception.JwtAuthException;
import com.whatsapp.whatsappclone.Exception.MessageException;
import com.whatsapp.whatsappclone.Exception.UserException;
import com.whatsapp.whatsappclone.JwtConfigServiceImpl.JwtService;
import com.whatsapp.whatsappclone.Repositry.ChatRepo;
import com.whatsapp.whatsappclone.Repositry.MessageRepo;
import com.whatsapp.whatsappclone.Repositry.UserRepo;
import com.whatsapp.whatsappclone.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ChatRepo chatRepo;
    @Autowired
    private JwtService jwtService;

    @Override
    public Message sendMessage(SendMessageDto Dto, String jwt) throws JwtAuthException, UserException, ChatException {
        if (jwtService.validateAuthToken(jwt)) {
            User reqUser = userRepo.findByEmail(jwtService.extractUsername(jwt)).get();
            if (reqUser == null) {
                throw new UserException("User Not Found !");
            } else {
                Chat chat = chatRepo.findById(Dto.getChatId()).orElseThrow(()-> new ChatException("Chat Not Founnd !"));
                Message message = new Message();
                message.setChat(chat);
                message.setUser(reqUser);
                message.setContent(Dto.getContent());
                message.setTimeStamp(LocalDateTime.now());
                messageRepo.save(message);
                return message;
            }
        } else {
            throw new JwtAuthException("Invalid Token !");
        }
    }

    @Override
    public List<Message> getChatById(Long chatId, String jwt) throws JwtAuthException, UserException, ChatException {
        if (jwtService.validateAuthToken(jwt)) {
            User reqUser = userRepo.findByEmail(jwtService.extractUsername(jwt)).get();
            if (reqUser == null) {
                throw new UserException("User Not Found !");
            } else {
                Chat chat = chatRepo.findById(chatId).orElseThrow(()-> new ChatException("Chat Not Found!"));
                if (chat.getUsers().contains(reqUser)) {
                    List<Message> messages = messageRepo.findByChatId(chatId);
                    return messages;
                } else {
                    throw new ChatException("You Are Not Register To This Chat");
                }
            }
        } else {
            throw new JwtAuthException("Invalid Token !");
        }
    }

    @Override
    public Message findMessageById(Long messageId) throws MessageException {
        Message message = messageRepo.findById(messageId).orElseThrow(()-> new MessageException("Message Not Found !"));
        return message;
    }

    @Override
    public void deleteMessage(Long messageId, String jwt) throws JwtAuthException, UserException, MessageException {
        if (jwtService.validateAuthToken(jwt)) {
            User reqUser = userRepo.findByEmail(jwtService.extractUsername(jwt)).get();
            if (reqUser == null) {
                throw new UserException("User Not found !");
            } else {
                Message message = messageRepo.findById(messageId).orElseThrow(()-> new MessageException("Message Not Found !"));
                if (message.getUser().getId().equals(reqUser.getId())) {
                    messageRepo.delete(message);
                } else {
                    throw new MessageException("You Can't Delete Another Message !");
                }
            }
        } else {
            throw new JwtAuthException("Invalid Token !");
        }
    }
}
