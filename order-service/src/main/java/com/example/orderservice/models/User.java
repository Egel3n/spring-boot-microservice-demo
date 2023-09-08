package com.example.orderservice.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    private String id;
    private String name;
    private String username;
    private String email;
    private String password;
    private List<UUID> orderIds;

    public User() {}

    public void addOrder(UUID id){
        orderIds.add(id);
    }

}


