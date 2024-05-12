# LoadBalancer
Table of Contents
-----------------

1.  Setting Up the Load Balancer
2.  Call Flow Diagrams
3.  Extending and Scaling the Implementation

Setting Up the Load Balancer
----------------------------

To set up the load balancer, follow these steps:

1.  Install Java Development Kit (JDK): You need to have JDK installed on your computer to compile and run Java code. If you don't have it installed, you can download it from the official Oracle website.
   
(Either use 3-4 option or directly open with Intellij go to step 5)

3.  Compile the Code: Open a command prompt or terminal window and navigate to the directory where you saved the `.java` file. Compile the Java code using the `javac` command like so:

    ```
    javac LoadBalancer.java

    ```
4.  Run the Code: Run the compiled Java code using the `java` command like so:

    ```
    java LoadBalancer

    ```

    The load balancer will start and listen for incoming HTTP requests on port 8000.
5. Run MockServer1, MockServer2, MockServer3
6. Run  LoadBalancer.java application
7. Open CMD and ```curl http://localhost:8000```

Call Flow Diagrams
------------------

High-Level Call Flow:

1.  The load balancer starts and listens for incoming HTTP requests (5001, 5002, 5003).
2.  When a request comes in, the load balancer selects a server based on the load balancing strategy (round-robin or random).
3.  The load balancer forwards the request to the selected server.
4.  The server processes the request and sends a response back to the load balancer.
5.  The load balancer forwards the response back to the client.

Low-Level Call Flow:

1.  The `main()` method in the `LoadBalancer` class creates an `HttpServer` and a `LoadBalancer` instance.
2.  The `LoadBalancer` instance schedules the `healthCheck() From HealthCheck` method to run at fixed intervals.
3.  The `HttpServer` starts listening for HTTP requests.
4.  When a request comes in, the `handleRequest()` method in the `LoadBalancer` class is called.
5.  The `handleRequest()` method calls the `getServer()` method on the load balancing strategy to select a server.
6.  The `handleRequest()` method forwards the request to the selected server and sends the server's response back to the client.

Extending and Scaling the Implementation
----------------------------------------

The current implementation can be extended or scaled in several ways:

1.  Add More Strategies: You can add more load balancing strategies by creating more classes that implement the `LoadBalancingStrategy` interface. For example, you could create a `LeastConnectionsStrategy` that selects the server with the fewest active connections.

2.  Add More Servers: You can add more servers to the load balancer by adding more URLs to the `servers` map in the `LoadBalancer` constructor.

3.  Improve Health Checks: The current health check is very basic and only checks if the server is reachable. we could improve it by checking other metrics like CPU usage, memory usage, or response time.

4.  Handle Failures: The current implementation does not handle failures. If a server goes down while processing a request, the request will fail. You could add error handling code to retry the request on a different server if the first attempt fails.

5.  Scale Horizontally: You can run multiple instances of the load balancer behind a reverse proxy to handle more traffic. The reverse proxy would distribute incoming requests among the load balancer instances, and each load balancer instance would distribute its requests among its servers.
