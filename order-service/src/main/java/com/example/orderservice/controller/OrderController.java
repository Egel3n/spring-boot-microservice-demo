package com.example.orderservice.controller;


import com.example.orderservice.business.OrderService;
import com.example.orderservice.models.Order;
import com.example.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/orders")
@AllArgsConstructor
public class OrderController {

    OrderRepository repo;
    OrderService service;

    @GetMapping("all")
    public List<Order> getAll(){
        return repo.findAll();
    }

 //   @GetMapping("add")
 //   public String add()
 //   {
 //       repo.insert(new Order("2",152, Arrays.asList("1"), LocalDate.now()));
 //       return "added";
 //   }
    @GetMapping("get/{id}")
    public Order getById(@PathVariable("id") UUID id){
        return repo.findById(id).get();
    }

    @GetMapping("test")
    public String sjsj(){

        service.createOrder("1",List.of("1","2","3"));
        return "works";
    }

//   @GetMapping("getProducts/{id}")
//   public List<Product> getProducts(@PathVariable("id") String orderId){
//       RestTemplate template = new RestTemplate();
//       var producIdList = repo.findById(orderId).get().getProducts();
//       List<Product> productList = new ArrayList<>();
//       producIdList.forEach(p-> productList.add(template.getForObject("http://localhost:7071/product/get/"+ p, Product.class)));

//       return productList;
//   }

    @PostMapping(path = "add")
    public String addOrder(@RequestBody Order order){
        repo.insert(order);
        return "added";
    }


}
