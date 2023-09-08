package com.example.orderservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@Getter
public class Product {
    @Id
    String id;
    String name;

    public Product() {
    }
}

