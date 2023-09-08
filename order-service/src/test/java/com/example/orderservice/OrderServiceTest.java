package com.example.orderservice;

import com.example.orderservice.business.OrderService;
import com.example.orderservice.models.Order;
import com.example.orderservice.models.Product;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.repository.ProductRepository;
import com.example.orderservice.repository.UserRepository;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OrderServiceTest {

     OrderService orderService;
     OrderRepository orderRepository;
     ProductRepository productRepository;
     UserRepository userRepository;

    @BeforeEach
    void SetUp(){
        orderRepository = Mockito.mock(OrderRepository.class);
        productRepository = Mockito.mock(ProductRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        orderService = new OrderService(orderRepository,productRepository,userRepository);
    }

    @Test
    public void orderCostShouldBeFifty(){
        List<String> productIds = List.of("1","2","3");
        Mockito.when(productRepository.findAll()).thenReturn(List.of(new Product("1","pen",15),
                new Product("2","rubber",15),
                new Product("3","book",20)));
        var order = orderService.createOrder("1",productIds);

        Assertions.assertEquals(50,order.getAmount());
    }

    @Test
    public void calculateDiscountShouldThrowIllegalArgumentException(){
        var randomUUID = UUID.randomUUID();
        Mockito.when(orderRepository.findAll()).thenReturn(List.of(new Order(randomUUID,-1,List.of("1","2"), LocalDate.now())));
        Assertions.assertThrows(IllegalArgumentException.class , ()-> orderService.calculateDiscount(randomUUID)) ;
    }

    @Test
    public void calculateDiscountShouldReturnOneThousand(){
        var randomUUID = UUID.randomUUID();
        Mockito.when(orderRepository.findAll()).thenReturn(List.of(new Order(randomUUID,10001,List.of("1","2"), LocalDate.now())));
        Assertions.assertEquals(1000, orderService.calculateDiscount(randomUUID));
    }



    @Test
    public void calculateDiscountShouldReturnSevenHundred(){
        var randomUUID = UUID.randomUUID();
        Mockito.when(orderRepository.findAll()).thenReturn(List.of(new Order(randomUUID,7001,List.of("1","2"), LocalDate.now())));
        Assertions.assertEquals(700, orderService.calculateDiscount(randomUUID));
    }
    @Test
    public void calculateDiscountShouldReturnFourHundred(){
        var randomUUID = UUID.randomUUID();
        Mockito.when(orderRepository.findAll()).thenReturn(List.of(new Order(randomUUID,4001,List.of("1","2"), LocalDate.now())));
        Assertions.assertEquals(400, orderService.calculateDiscount(randomUUID));
    }

    @Test
    public void calculateDiscountShouldReturnTwoHundred(){
        var randomUUID = UUID.randomUUID();
        Mockito.when(orderRepository.findAll()).thenReturn(List.of(new Order(randomUUID,2001,List.of("1","2"), LocalDate.now())));
        Assertions.assertEquals(200, orderService.calculateDiscount(randomUUID));
    }

    @Test
    public void calculateDiscountShouldReturnZero(){
        var randomUUID = UUID.randomUUID();
        Mockito.when(orderRepository.findAll()).thenReturn(List.of(new Order(randomUUID,1999,List.of("1","2"), LocalDate.now())));
        Assertions.assertEquals(0, orderService.calculateDiscount(randomUUID));
    }



    @Test
    public void applyDiscountShouldReturnThreeThousandSixHundredOne(){
        var randomUUID = UUID.randomUUID();
        Mockito.when(orderRepository.findAll()).thenReturn(List.of(new Order(randomUUID,4001,List.of("1","2"), LocalDate.now())));
        var discount =  orderService.calculateDiscount(randomUUID);
        Assertions.assertEquals(3601,orderService.applyDiscount(randomUUID,discount));
    }

    @Test
    public void applyDiscountShouldThrowNotFoundException(){
        Mockito.when(orderRepository.findAll()).thenReturn(List.of(new Order(UUID.randomUUID(),555,List.of("1","2"), LocalDate.now())));
       Assertions.assertThrows(RuntimeException.class, () -> orderService.applyDiscount(UUID.randomUUID(),5));
    }





}
