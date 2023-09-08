package com.example.userservice.controller;



import com.example.userservice.model.Error;
import com.example.userservice.model.Product;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.netty.handler.timeout.TimeoutException;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;

import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalTime;

import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
@ControllerAdvice
public class UserController {

    UserRepository repo;
    RestTemplate restTemplate;



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

    private static final String USER_SERVICE= "userService";


    public List<Product> getAllProducts(){
        System.out.println("retry attempt time: "+ LocalTime.now());
        return restTemplate.getForObject("http://localhost:7071/api/products/all", List.class);
    }

    public List<Product> testFallback(){
        return List.of(new Product("1","fake",123),new Product("2","fake2",123));
    }

    @GetMapping("test")
    public List<Product> retryableGetALLProducts() {
        RetryConfig config = RetryConfig.custom().maxAttempts(5).waitDuration(Duration.ofMillis(1000)).build();
        Retry retry = Retry.of("backend",config);
        Supplier<List<Product>> supp = Retry.decorateSupplier(retry,this::getAllProducts);
        var result = Try.ofSupplier(supp).recover(throwable -> testFallback()).get();
        return result;

    }


    @GetMapping("timeout")
    public List<Product> timeoutGetALLProducts() throws ExecutionException, InterruptedException {
            TimeLimiterConfig config = TimeLimiterConfig.custom()
                    .cancelRunningFuture(true)
                    .timeoutDuration(Duration.ofMillis(1))
                    .build();
            TimeLimiter timeLimiter = TimeLimiter.of("backend",config);
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
            List<Product> result = timeLimiter.executeCompletionStage(scheduler,
                    ()->CompletableFuture.supplyAsync(this::getAllProducts)).toCompletableFuture().get();
            return result;
    }


}
