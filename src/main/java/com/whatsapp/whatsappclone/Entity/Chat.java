package com.whatsapp.whatsappclone.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String chat_name;

    private String chat_image;
    @Column(name = "is_group")
    private boolean isGroup = false;
    @JoinColumn(name = "created_by")
    @ManyToOne
    @JsonIgnore
    private User createdBy;
    @ManyToMany
    @JsonIgnore
    private Set<User> users = new HashSet<>();
    @OneToMany
    @JsonIgnore
    private List<Message> messages = new ArrayList<>();
    @ManyToMany
    @JsonIgnore
    private Set<User> admin = new HashSet<>();
}
