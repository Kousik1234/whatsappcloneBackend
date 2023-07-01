package com.whatsapp.whatsappclone.Service;

import com.whatsapp.whatsappclone.DTO.SendMessageDto;
import com.whatsapp.whatsappclone.Entity.Message;
import com.whatsapp.whatsappclone.Exception.ChatException;
import com.whatsapp.whatsappclone.Exception.JwtAuthException;
import com.whatsapp.whatsappclone.Exception.MessageException;
import com.whatsapp.whatsappclone.Exception.UserException;

import java.util.List;

public interface MessageService {

    public Message sendMessage (SendMessageDto Dto , String jwt) throws JwtAuthException , UserException , ChatException;

    public List<Message> getChatById(Long chatId , String jwt) throws JwtAuthException , UserException , ChatException;

    public Message findMessageById(Long messageId) throws MessageException;

    public void deleteMessage(Long messageId , String jwt) throws JwtAuthException , UserException , MessageException;

}
