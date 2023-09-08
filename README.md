# Visualizer Documentation

##### Visualizer helps us monitor the live status of Docker containers. But the best part is that it provides auto-scalability for containers.
### To set it up, you need to follow these steps:

1. You need to add below configs to top of your docker-compose file. They are required for the visualizer.
```
environment:
      - VISUALIZER_TYPE=manager
      - VISUALIZER_TASK=true
      - VISUALIZER_TASK_AUTOSCALE=true
    image: yandeu/visualizer:dev
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
    networks:
      - agent_network
    deploy:
      mode: replicated
      replicas: 1
      placement:
        constraints: [node.role == manager]
    labels:
      - visualizer.manager
    healthcheck:
      test: curl -f http://localhost:3500/healthcheck || exit 1
      interval: 10s
      timeout: 2s
      retries: 3
      start_period: 5s
    ports:
      - '9500:3500'

  agent:
    environment:
      - VISUALIZER_TYPE=agent
      - VISUALIZER_TASK=true
      - VISUALIZER_TASK_AUTOSCALE=true
    image: yandeu/visualizer:dev
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
    networks:
      - agent_network
    deploy:
      mode: global
      placement:
        constraints: [node.platform.os == linux]
    labels:
      - visualizer.agent
    healthcheck:
      test: curl -f http://localhost:9501/healthcheck || exit 1
      interval: 10s
      timeout: 2s
      retries: 3
      start_period: 5s


 networks:
  agent_network:
    driver: overlay
    attachable: true
```
2.if you add this attributes to your container,
	You can see that when your container's CPU usage exceeds 20%, it will automatically create an instance of the same container, and they will share the load
```
your-container:
	...
	...
	...
    deploy:
      	  resources:
            limits:
              cpus: "0.20"
	labels:
   - visualizer.autoscale.min=1
   - visualizer.autoscale.max=5
   - visualizer.autoscale.up.cpu=0.1
   - visualizer.autoscale.down.cpu=0.05
 ```
 
 3. After these three steps, you will be ready to deploy your project to Docker. 
  However, Visualizer only works on ***Docker Swarm***.
So you need to run:
```
$docker swarm init
$docker stack deploy -c docker-compose.yml visualizer
```
5. go to [http://127.0.0.1:9500](http://127.0.0.1:9500) and you will see ***visualizer dashboard***

##### For a better documentation and more features, you can check the [Visualizer Documentation](https://hub.docker.com/r/yandeu/visualizer).
