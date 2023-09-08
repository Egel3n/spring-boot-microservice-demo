package com.example.userservice.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@AllArgsConstructor
@Getter
public class User {

    @Id
    String id;
    String name;
    String username;
    String email;
    String password;
    List<String> orderIds;

    public User() {
    }
}


