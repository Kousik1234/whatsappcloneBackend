package com.whatsapp.whatsappclone.ServiceImpl;

import com.whatsapp.whatsappclone.DTO.GroupChatDto;
import com.whatsapp.whatsappclone.Entity.Chat;
import com.whatsapp.whatsappclone.Entity.User;
import com.whatsapp.whatsappclone.Exception.ChatException;
import com.whatsapp.whatsappclone.Exception.JwtAuthException;
import com.whatsapp.whatsappclone.Exception.UnAuthorizeException;
import com.whatsapp.whatsappclone.Exception.UserException;
import com.whatsapp.whatsappclone.JwtConfigServiceImpl.JwtService;
import com.whatsapp.whatsappclone.Repositry.ChatRepo;
import com.whatsapp.whatsappclone.Repositry.UserRepo;
import com.whatsapp.whatsappclone.Service.ChatService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepo chatRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private EntityManager entityManager;

    @Override
    public Chat createChat(String jwt, Long recUserId) throws UserException , JwtAuthException {

        if (jwtService.validateAuthToken(jwt)) {
            String username = jwtService.extractUsername(jwt);
            Optional<User> dbreqUser = userRepo.findByEmail(username);
            if (dbreqUser.isEmpty()) {
                throw new UserException("User Not Found !");
            } else {
                User reqUser = dbreqUser.get();
                User recUser = userRepo.findById(recUserId).orElseThrow(()-> new UserException("User Not Found !"));
                Chat isChatExist = chatRepo.findSingleChatByUserIds(recUser,reqUser);
                if (isChatExist!=null) {
                    return isChatExist;
                } else {
                    Chat chat = new Chat();
                    chat.setCreatedBy(reqUser);
                    chat.getUsers().add(reqUser);
                    chat.getUsers().add(recUser);
                    chatRepo.save(chat);
                    return chat;
                }
            }
        } else {
            throw new JwtAuthException("Ivalid Jwt Token !");
        }
    }

    @Override
    public List<Chat> findAllChatByUserId(String jwt) throws UserException , JwtAuthException{
        if (jwtService.validateAuthToken(jwt)) {
            User user = userRepo.findByEmail(jwtService.extractUsername(jwt)).get();
            if (user == null) {
                throw new UserException("User Not Found !");
            } else {
                List<Chat> chats = chatRepo.findChatByUserId(user.getId());
                return chats;
            }
        } else {
            throw new JwtAuthException("Invalid Token");
        }
    }

    @Override
    public Chat createGroup(GroupChatDto Dto, String jwt) throws UserException ,JwtAuthException {
        if (jwtService.validateAuthToken(jwt)) {
            User reqUser = userRepo.findByEmail(jwtService.extractUsername(jwt)).get();
            if (reqUser == null) {
                throw new UserException("User Not Found !");
            } else {
                List<Long> uids = Dto.getUserIds();
                Chat group = new Chat();
                group.setCreatedBy(reqUser);
                group.setGroup(true);
                group.setChat_image(Dto.getChat_image());
                group.setChat_name(Dto.getChat_name());
                group.getAdmin().add(reqUser);
                List<User> users = entityManager
                        .createNativeQuery("SELECT * FROM user WHERE id IN (:uids)", User.class)
                        .setParameter("uids", uids)
                        .getResultList();

                Set<User> userSet = new HashSet<>(users);

                group.getUsers().addAll(userSet);

                chatRepo.save(group);

                return group;
            }
        } else {
            throw new JwtAuthException("Invalid Token !");
        }
    }

    @Override
    public Chat addUserToGroup(List<Long> userIds, Long chatId , String jwt) throws JwtAuthException , UnAuthorizeException , UserException, ChatException {
        if (jwtService.validateAuthToken(jwt)) {
            User reqUser = userRepo.findByEmail(jwtService.extractUsername(jwt)).get();
            if (reqUser == null) {
                throw new UserException("User Not Found !");
            } else {
                Chat chat = chatRepo.findById(chatId).orElseThrow(()-> new ChatException("Chat Not Found !"));
                List<Long> uids = userIds;
                if (chat.getAdmin().contains(reqUser)) {
                    List<User> users = entityManager
                            .createNativeQuery("SELECT * FROM user WHERE id IN (:uids)", User.class)
                            .setParameter("uids", uids)
                            .getResultList();

                    Set<User> userSet = new HashSet<>(users);

                    chat.getUsers().addAll(userSet);
                    chatRepo.save(chat);
                    return chat;
                } else {
                    throw new UnAuthorizeException("You Don't Have Permission To Add Users");
                }
            }
        } else {
            throw new JwtAuthException("Invalid Token");
        }
    }

    @Override
    public Chat renameGroup(Long chatId, String groupName, String jwt) throws JwtAuthException , UnAuthorizeException , UserException, ChatException {
        if (jwtService.validateAuthToken(jwt)) {
            User reqUser = userRepo.findByEmail(jwtService.extractUsername(jwt)).get();
            if (reqUser == null) {
                throw  new UserException("User Not Found !");
            } else {
                Chat chat = chatRepo.findById(chatId).orElseThrow(()-> new ChatException("Chat Not Found !"));
                if (chat.getAdmin().contains(reqUser)) {
                    chat.setChat_name(groupName);
                    chatRepo.save(chat);
                    return chat;
                } else {
                    throw new UnAuthorizeException("You Are Not Admin");
                }
            }
        } else {
            throw new JwtAuthException("Invalid Token");
        }
    }

    @Override
    public Chat removeFromGroup(Long chatId, Long userId, String jwt) throws JwtAuthException , UnAuthorizeException, UserException, ChatException {
        if (jwtService.validateAuthToken(jwt)) {
            User requser = userRepo.findByEmail(jwtService.extractUsername(jwt)).get();
            if (requser == null) {
                throw new UserException("User Not Found !");
            } else {
                Chat chat = chatRepo.findById(chatId).orElseThrow(()-> new ChatException("Chat Not Found !"));
                User user = userRepo.findById(userId).orElseThrow(()-> new UserException("User Not Found !"));
                if (chat.getAdmin().contains(requser) && chat.getUsers().contains(requser)) {
                    if (user.getId().equals(requser.getId())) {
                        chat.getUsers().remove(requser);
                        return chat;
                    } else {
                        chat.getUsers().remove(user);
                        return chat;
                    }
                } else if (chat.getUsers().contains(user)) {
                    chat.getUsers().remove(user);
                    return chat;
                } else {
                    throw new UnAuthorizeException("You Are Not Admin !");
                }
            }
        } else {
            throw new JwtAuthException("Invalid Token !");
        }
    }

    @Override
    public void deleteChat(Long chatId, String jwt) throws JwtAuthException , UserException, ChatException {

        if (jwtService.validateAuthToken(jwt)) {
            User user = userRepo.findByEmail(jwtService.extractUsername(jwt)).get();
            if (user == null) {
                throw  new UserException("User Not Found !");
            } else {
                Chat chat = chatRepo.findById(chatId).orElseThrow(()-> new ChatException("Chat Not Found !"));
                if (chat.getUsers().contains(user)) {
                    chatRepo.delete(chat);
                } else {
                    throw new ChatException("You Are Not In This Group !");
                }
            }
        } else {
            throw new JwtAuthException("Invalid Token !");
        }
    }
}
