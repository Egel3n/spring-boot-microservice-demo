package com.example.producservice.controller;


import com.example.producservice.model.Product;
import com.example.producservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
@AllArgsConstructor
public class ProductController {

    ProductRepository repo;

    @GetMapping("all")
    public List<Product> getAllProduct(){
        return  repo.findAll();
    }

  //  @GetMapping(path = "add")
  //  public String addProduct(){
  //      Product product = new Product("3","silgi",123);
  //      repo.insert(product);
  //      repo.save(product);
  //      return "added";
  //  }

    @GetMapping("get/{id}")
    public Product getById(@PathVariable("id") String id){
        return repo.findById(id).get();
    }

    @PostMapping("add")
    public String addProduct(@RequestBody Product product){
        repo.insert(product);
        return "added";
    }

}
