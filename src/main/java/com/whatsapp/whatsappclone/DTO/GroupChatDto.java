package com.whatsapp.whatsappclone.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupChatDto {

    private List<Long> userIds;

    private String chat_name;

    private String chat_image;

}
