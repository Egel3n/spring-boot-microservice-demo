package com.example.userservice.controller;


import com.example.userservice.model.Order;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
public class UserController {

    UserRepository repo;

    @GetMapping("all")
    public List<User> getALlUsers()
    {
        var list = repo.findAll();
        System.out.println(list.toString());
        return list;
    }

    @GetMapping("get/{id}")
    public User getById(@PathVariable("id") String id){
        return repo.findById(id).get();
    }

 //  @GetMapping("add")
 //  public String addUser(){
 //      User user = new User("1","ege","egelen","test@gmail.com","123", Arrays.asList("1","2"));
 //      repo.insert(user);
 //      return "added";

 //  }

//    @GetMapping("orders/{id}")
//    public List<Order> getOrders(@PathVariable("id") String id){
//        RestTemplate template = new RestTemplate();
//       var user =  repo.findById(id).get();
//       var orderList = user.getOrderIds();
//       List<Order> orders = new ArrayList<>();
//       orderList.forEach(o-> orders.add(template.getForObject("http://localhost:7072/order/get/"+o,Order.class)));
//       return orders;
//    }

    @PostMapping("add")
    public String addUser(@RequestBody User user){
        repo.insert(user);
        return "added";
    }

}
