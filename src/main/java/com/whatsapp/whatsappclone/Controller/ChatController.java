package com.whatsapp.whatsappclone.Controller;

import com.whatsapp.whatsappclone.DTO.ErrorMessageDto;
import com.whatsapp.whatsappclone.DTO.GroupChatDto;
import com.whatsapp.whatsappclone.Entity.Chat;
import com.whatsapp.whatsappclone.Exception.ChatException;
import com.whatsapp.whatsappclone.Exception.JwtAuthException;
import com.whatsapp.whatsappclone.Exception.UnAuthorizeException;
import com.whatsapp.whatsappclone.Exception.UserException;
import com.whatsapp.whatsappclone.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "*")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("create/chat")
    public ResponseEntity<?> createChatHandeller(@RequestParam String jwt, @RequestParam Long recUserId) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto();

        try {
            Chat chat = chatService.createChat(jwt, recUserId);
            return new ResponseEntity<>(chat, HttpStatus.CREATED);
        } catch (UserException | JwtAuthException e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("allChat/user")
    public ResponseEntity<?> findAllChatByUserIdHandeller(@RequestParam String jwt) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto();

        try {
            List<Chat> chats = chatService.findAllChatByUserId(jwt);
            return new ResponseEntity<>(chats, HttpStatus.CREATED);
        } catch (UserException | JwtAuthException e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("create/group")
    public ResponseEntity<?> createGroupHandeller(@RequestBody GroupChatDto Dto , @RequestParam String jwt) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto();

        try {
            Chat chat = chatService.createGroup(Dto, jwt);
            return new ResponseEntity<>(chat, HttpStatus.CREATED);
        } catch (UserException | JwtAuthException e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("addUsers/group")
    public ResponseEntity<?> addUserToGroupHandeller(@RequestParam List<Long> userIds , @RequestParam Long chatId , @RequestParam String jwt) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto();

        try {
            Chat chat = chatService.addUserToGroup(userIds, chatId, jwt);
            return new ResponseEntity<>(chat, HttpStatus.CREATED);
        } catch (UserException | ChatException | JwtAuthException e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto, HttpStatus.BAD_REQUEST);
        } catch (UnAuthorizeException e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto,HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("rename/group")
    public ResponseEntity<?> renameGroupHandeller(@RequestParam Long chatId , @RequestParam String groupName , @RequestParam String jwt) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto();

        try {
            Chat chat = chatService.renameGroup(chatId, groupName, jwt);
            return new ResponseEntity<>(chat, HttpStatus.CREATED);
        } catch (UserException | ChatException | JwtAuthException e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto, HttpStatus.BAD_REQUEST);
        } catch (UnAuthorizeException e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto,HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("user/remove/group")
    public ResponseEntity<?> removeFromGroupHandeller(@RequestParam Long chatId , @RequestParam Long userId , @RequestParam String jwt) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto();

        try {
            Chat chat = chatService.removeFromGroup(chatId, userId, jwt);
            return new ResponseEntity<>(chat, HttpStatus.OK);
        } catch (UserException | ChatException | JwtAuthException e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto, HttpStatus.BAD_REQUEST);
        } catch (UnAuthorizeException e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto,HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("delete/chat")
    public ResponseEntity<?> deleteChatHandeller(@RequestParam Long chatId , @RequestParam String jwt) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto();

        try {
            chatService.deleteChat(chatId, jwt);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (UserException | ChatException | JwtAuthException e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
