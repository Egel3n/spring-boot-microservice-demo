# Tests Documentation 
##### In this branch, I have implemented unit tests and integration tests
---
### For unit tests:
 
JUnit and Mockito libraries are used for unit testing.
With `Mockito.mock(ExampleRepository.class)`, you can create a mock object
and to make that object functional, you need to define its behavior.
With `Mockito.when(ExampleRepoisotory).thenReturn(ExampleReturnObject)`, you can specify its behavior.
All these allow you to test parts of your application independently from your database.
Add the `@Test` annotation to your test method,
and that's all. You can run your test class and test your code. You can find my implementation under the order-service application.


---
### For integration tests:


Testcontainers library and Docker are used for integration tests. Testcontainers provides testing for the database relational part of the code. It creates temporary database containers, then applies the 	tests, and finally removes the containers. Let's see how to apply it.

1. you need a DB container.(I will be using mongoDB here's the [documantation for other databases](https://testcontainers.com/modules/)

```
@Configuration
@EnableMongoRepositories
public class MongoDBTestContainer {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest")
            .withExposedPorts(27017);

    static {
        mongoDBContainer.start();
        var mappedPort = mongoDBContainer.getMappedPort(27017);
        System.setProperty("mongodb.container.port", String.valueOf(mappedPort));
    }
}
```

2. Add this annotations to your test class
```
@DataMongoTest
@Testcontainers
@ContextConfiguration(classes = MongoDBTestContainer.class)
public class ProductRepositoryTest {
```
3. Add these to application.properties file which is located src/test/resources
```
 ,[JUnit]() and [Mockito]	spring.data.mongodb.database=OAuth2Sample
	spring.data.mongodb().port=${mongodb.container.port}
	spring.data.mongodb.host=localhost
	spring.data.mongodb.auto-index-creation=true
```

##### now you can test your database in your test class.

#### For their better documentation: [TestContainers](https://java.testcontainers.org/) , [JUnit](https://junit.org/junit5/docs/current/user-guide/) and [Mockito](https://site.mockito.org/) 
