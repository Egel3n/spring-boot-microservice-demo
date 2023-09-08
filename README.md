# Spring-Boot-Admin

##### In this branch, the admin server and the config server are implemented. You will see how to set these up.
---
### Admin Server
##### In short, the admin server is a tool for monitoring your API's status
#### Setup Steps:
 ##### 1. Create a Spring Boot web application with the dependency spring-boot-admin. Alternatively, you can simply add this dependency to pom.xml.
```
<dependency>
    <groupId>de.codecentric</groupId>
    <artifactId>spring-boot-admin-starter-server</artifactId>
    <version>3.1.6</version>
</dependency>
```

##### 2. Add this dependency to other APIs that you want to monitor

```
<dependency>
    <groupId>de.codecentric</groupId>
    <artifactId>spring-boot-admin-starter-client</artifactId>
    <version>3.1.6</version>
</dependency>
```

##### 3. Go to your admin server's port and easily check your APIs


---
### Config Server

##### Config server is a tool that provides lots of benefits. you can easily access your project's config files thanks to the config server during development and after publishing.
##### And its setup is as easy as the admin server's setup.

#### Setup Steps:
##### 1.add this dependency to your config-server's pom.xml.
```
  <dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-config-server</artifactId>
  </dependency>
```
##### 2. use `@EnableConfigServer` annotation in your config server application class.

##### 3. add `spring.cloud.config.server.git.uri = "your-git-repo-adress"` to your application.properties file.
---
#### Server is done. It's client's turn.


##### 1. first add this to client's pom.xml.
```
<dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-config</artifactId>
    </dependency>
```
##### 2.  `spring.cloud.config.uri=http://localhost:{your-config-server-port-here}`, add this to client's application.properties file.
### Final Steps :
you need to add your .properties or .yml files to your Git repository. 

You have to name the files according to the application names. 
For the user-service application, you need to name the file as user-service.properties, 
and it will automatically provide the correct config file for your application.
#### For their better documentations: [Config-Server](https://www.springcloud.io/post/2022-03/spring-cloud-config-server-step-by-step/#gsc.tab=0) and [Spring-Boot-Admin](http://docs.spring-boot-admin.com/3.0.2/getting-started.html)
---
### In My Project
you can find the config server on [http://localhost:8888](http://localhost:8888) and the admin server on [http://localhost:8080](http://localhost:8080)

