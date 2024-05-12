package Driver;

import Helper.HealthCheck;
import StrategyPattern.LoadBalancingStrategy;
import StrategyPattern.RoundRobinStrategy;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
public class LoadBalancer {
    private ConcurrentHashMap<String, Boolean> servers = new ConcurrentHashMap<>();
    private LoadBalancingStrategy strategy = new RoundRobinStrategy();
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    public LoadBalancer() {
        // Add servers to the list
        servers.put("http://localhost:5001", true);
        servers.put("http://localhost:5002", true);
        servers.put("http://localhost:5003", true);


        // Start the health check task
        executorService.scheduleAtFixedRate(new HealthCheck(servers), 0, 5, TimeUnit.SECONDS);
    }

    public void handleRequest(HttpExchange exchange) throws IOException {
        String server = strategy.getServer(servers);
        HttpURLConnection conn = (HttpURLConnection) new URL(server).openConnection();

        // Forward the request
        conn.setRequestMethod(exchange.getRequestMethod());
        if ("POST".equals(exchange.getRequestMethod())) {
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            byte[] buffer = new byte[2048];
            int read;
            while ((read = exchange.getRequestBody().read(buffer)) != -1) {
                os.write(buffer, 0, read);
            }
            os.close();
        }

        // Send the response
        exchange.sendResponseHeaders(conn.getResponseCode(), 0);
        InputStream is = conn.getInputStream();
        OutputStream os = exchange.getResponseBody();
        byte[] buffer = new byte[2048];
        int read;
        while ((read = is.read(buffer)) != -1) {
            os.write(buffer, 0, read);
        }
        os.close();
    }

    public void setStrategy(LoadBalancingStrategy strategy) {
        this.strategy = strategy;
    }

    public static void main(String[] args) {
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8000), 0);
            LoadBalancer loadBalancer = new LoadBalancer();
            server.createContext("/", loadBalancer::handleRequest);
            server.start();
            //way to change loadbalancer strategy
            //loadBalancer.setStrategy(new StrategyPattern.RandomStrategy());
        } catch (IOException e) {
            System.err.println("Error occurred while setting up server: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }finally {
            if (server != null) {
                server.stop(0);
            }
        }
    }
}