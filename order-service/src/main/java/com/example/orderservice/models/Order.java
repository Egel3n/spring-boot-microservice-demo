package com.example.orderservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document
@AllArgsConstructor
@Getter
public class Order {
    @Id
    String id;

    int amount;
    List<String> products;
    LocalDate orderDate;

}
