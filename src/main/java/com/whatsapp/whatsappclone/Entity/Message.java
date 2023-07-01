package com.whatsapp.whatsappclone.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String content;

    private LocalDateTime timeStamp;
    @ManyToOne
    @JsonIgnore
    private User user;
    @ManyToOne
    @JsonIgnore
    private Chat chat;
}
