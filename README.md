# Circuit Breaker
##### The Resilience4j library is used for implementing two different circuit breakers: 
***Retry and Timeout***.

## Retry
Retry allows the request sender application to continue trying to send requests after failures, and after a specific number of retry attempts, it breaks the circuit. In this way, instead of showing an error to a user, we can display a **prepared data** or a **cached data**.

##### 1. Add this dependency to pom.xml file.
```
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
    </dependency>
```

##### 2.You pass the maximum number of retry attempts as a parameter and call your method by using `Retry.decorateSupplier(retry,this::getAllProducts)`.
```
RetryConfig config = RetryConfig.custom().maxAttempts(5).waitDuration(Duration.ofMillis(1000)).build();
        Retry retry = Retry.of("name",config);
        Supplier<List<Product>> supp = Retry.decorateSupplier(retry,this::getAllProducts);
        var result = Try.ofSupplier(supp).recover(throwable -> testFallback()).get();
````
##### The fallback method is the method that runs after failures.

## Timeout
Timeout is used for situations where, after waiting for some time, if the response has not come back yet, **you need to terminate that process to be faster and avoid keeping threads busy**.

```
TimeLimiterConfig config = TimeLimiterConfig.custom()
                    .cancelRunningFuture(true)
                    .timeoutDuration(Duration.ofMillis(1))
                    .build();
            TimeLimiter timeLimiter = TimeLimiter.of("backend",config);
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
            List<Product> result = timeLimiter.executeCompletionStage(scheduler,
                    ()->CompletableFuture.supplyAsync(this::getAllProducts)).toCompletableFuture().get();
            return result;
```
#### For its original document: [Resilience4j](https://github.com/resilience4j/resilience4j)

---
# In My Application
##### You can check my implementation under user-service.