package com.example.orderservice.business;

import com.example.orderservice.models.Order;
import com.example.orderservice.models.Product;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.repository.ProductRepository;
import com.example.orderservice.repository.UserRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLEngineResult;
import java.time.LocalDate;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;



@Service
public class OrderService {


    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }


    public Order createOrder(String userId, List<String> productIds){
        Order newOrder = new Order(UUID.randomUUID());
        newOrder.setProducts(productIds);
        newOrder.setAmount(getTotalCostofProducts(newOrder));
        newOrder.setOrderDate(LocalDate.now());
        addOrderToUser(userId,newOrder);
        orderRepository.insert(newOrder);
        return newOrder;
    }

    private int getTotalCostofProducts(Order order){
        AtomicInteger totalCost = new AtomicInteger();
        totalCost.set(0);
        List<String> productIds = order.getProducts();
        List<Product> allProducts = productRepository.findAll();

        productIds.forEach(p-> {
            allProducts.forEach(
                    product -> {
                        if (product.getId() == p)
                            totalCost.addAndGet(product.getPrice());
                    }
            );
        });

        return totalCost.get();
    }

    private Order  addOrderToUser(String userId, Order order){
        var orderID = order.getId();
        var allUsers = userRepository.findAll();
       allUsers.forEach(user-> {
           if (user.getId() == userId)
               user.addOrder(orderID);
       });
        return order;
    }

    public int calculateDiscount(UUID orderId)
    {
        var allOrders = orderRepository.findAll();
        AtomicInteger orderAmountAtomic = new AtomicInteger();
        allOrders.forEach(order -> {
            if (order.getId() == orderId)
                orderAmountAtomic.set(order.getAmount());
        });
        int orderAmount = orderAmountAtomic.get();
        if(orderAmount <= 0)
            throw new IllegalArgumentException("Value must be non-negative or zero");
        else if (orderAmount>10000)
            return 1000;
        else if(orderAmount>7000)
            return 700;
        else if (orderAmount > 4000)
            return 400;
        else if (orderAmount>2000)
            return 200;
        else
            return 0;
    }

    public int applyDiscount(UUID orderId,int discount){
        var allOrders = orderRepository.findAll();

        for(Order order : allOrders){
            if (order.getId()==orderId){
                order.setAmount(order.getAmount()-discount);
                return order.getAmount();
            }
        }
        throw new RuntimeException("order cannot be found.");
    }
}