package com.whatsapp.whatsappclone.Controller;

import com.whatsapp.whatsappclone.DTO.ErrorMessageDto;
import com.whatsapp.whatsappclone.DTO.SendMessageDto;
import com.whatsapp.whatsappclone.Entity.Message;
import com.whatsapp.whatsappclone.Exception.ChatException;
import com.whatsapp.whatsappclone.Exception.JwtAuthException;
import com.whatsapp.whatsappclone.Exception.MessageException;
import com.whatsapp.whatsappclone.Exception.UserException;
import com.whatsapp.whatsappclone.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "*")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("send/message")
    public ResponseEntity<?> sendMessageHnadeller(@RequestBody SendMessageDto Dto , @RequestParam String jwt) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto();
        try {
            Message message = messageService.sendMessage(Dto, jwt);
            return new ResponseEntity<>(message,HttpStatus.OK);
        } catch (JwtAuthException | UserException | ChatException e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto,HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("All/chat")
    public ResponseEntity<?> getChatByIdHnadeller(@RequestParam Long chatId , @RequestParam String jwt) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto();
        try {
            List<Message> messages = messageService.getChatById(chatId, jwt);
            return new ResponseEntity<>(messages,HttpStatus.OK);
        } catch (JwtAuthException | UserException | ChatException e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto,HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("delete/message")
    public ResponseEntity<?> deleteMessageHnadeller(@RequestParam Long messageId , @RequestParam String jwt) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto();
        try {
            messageService.deleteMessage(messageId, jwt);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (JwtAuthException | UserException | MessageException e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto,HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("message")
    public ResponseEntity<?> findMessageByIdHnadeller(@RequestParam Long messageId ) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto();
        try {
            Message message = messageService.findMessageById(messageId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MessageException e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto,HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
