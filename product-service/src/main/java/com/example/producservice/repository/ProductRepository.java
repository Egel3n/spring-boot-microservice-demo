package com.example.producservice.repository;

import com.example.producservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product,String> {

    Product findProdcutByName(String Name);

}
