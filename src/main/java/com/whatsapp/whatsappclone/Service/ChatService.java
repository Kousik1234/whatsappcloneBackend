package com.whatsapp.whatsappclone.Service;

import com.whatsapp.whatsappclone.DTO.GroupChatDto;
import com.whatsapp.whatsappclone.Entity.Chat;
import com.whatsapp.whatsappclone.Exception.ChatException;
import com.whatsapp.whatsappclone.Exception.JwtAuthException;
import com.whatsapp.whatsappclone.Exception.UnAuthorizeException;
import com.whatsapp.whatsappclone.Exception.UserException;

import java.util.List;

public interface ChatService {

    public Chat createChat(String jwt , Long recUserId) throws UserException , JwtAuthException;

    public List<Chat> findAllChatByUserId(String jwt) throws UserException , JwtAuthException;

    public Chat createGroup(GroupChatDto Dto , String jwt) throws UserException , JwtAuthException;

    public Chat addUserToGroup(List<Long> userIds , Long chatId , String jwt) throws JwtAuthException, UnAuthorizeException , UserException,ChatException;

    public Chat renameGroup(Long chatId , String groupName , String jwt) throws JwtAuthException , UnAuthorizeException , UserException , ChatException;

    public Chat removeFromGroup(Long chatId , Long userId , String jwt) throws JwtAuthException , UnAuthorizeException , UserException , ChatException;

    public void deleteChat(Long chatId , String jwt) throws JwtAuthException , UserException , ChatException;

}
