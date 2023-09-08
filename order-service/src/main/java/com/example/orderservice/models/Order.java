package com.example.orderservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Document
@AllArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    private UUID id;
    private int amount;
    private List<String> products;
    private LocalDate orderDate;

    public Order(UUID id){}

    public Order() {
    }


    public void addProduct(String id){
        products.add(id);
    }

}
