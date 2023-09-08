package com.example.producservice.integration;

import com.example.producservice.MongoDBTestContainer;
import com.example.producservice.model.Product;
import com.example.producservice.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;


import java.util.List;

@DataMongoTest
@Testcontainers
@ContextConfiguration(classes = MongoDBTestContainer.class)
public class ProductRepositoryTest {


    @Autowired
    private ProductRepository productRepository;


    @Test
    void allProductsSizeShouldEqualThree(){
        List<Product> products = List.of(new Product("1","silgi",5),
                new Product("2","masa",10),new Product("3","kalem",15));
        productRepository.insert(products);
        var allProducts = productRepository.findAll();
        Assertions.assertEquals(3,allProducts.size());
    }


    @Test
    void allProductsSizeShouldNotEqualTwo(){
        List<Product> products = List.of(new Product("1","silgi",5),
                new Product("2","masa",10),new Product("3","kalem",15));
        productRepository.insert(products);
        var allProducts = productRepository.findAll();
        Assertions.assertNotEquals(2,allProducts.size());
    }

}
