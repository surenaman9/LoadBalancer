package IntegrationTests;

import Driver.LoadBalancer;
import Servers.MockServer1;
import Servers.MockServer2;
import Servers.MockServer3;
import StrategyPattern.RoundRobinStrategy;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestRoundRobinStrategyLoadBalancer {
    private static LoadBalancer loadBalancer;

    @BeforeClass
    public static void setUp() throws Exception {
        // Start the servers
        String[] args = {};
        MockServer1.main(args);
        MockServer2.main(args);
        MockServer3.main(args);

        loadBalancer = new LoadBalancer();
        loadBalancer.main(new String[]{});
    }

    @Test
    public void TestRoundRobinStrategy_Success() throws Exception {
        loadBalancer.setStrategy(new RoundRobinStrategy());

        Set<String> responses = new HashSet<>();

        for (int i = 0; i < 15; i++) {
            URL url = new URL("http://localhost:8000");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Send GET request
            conn.setRequestMethod("GET");

            // Get the response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Close connections
            in.close();
            conn.disconnect();
            if(i%3==0)
                assertEquals("This is the response from Mock Server 1", content.toString());
            if(i%3==1)
                assertEquals("This is the response from Mock Server 2", content.toString());
            if(i%3==2)
                assertEquals("This is the response from Mock Server 3", content.toString());
            responses.add(content.toString());
        }

        // Check that responses were received from all servers
        assertTrue(responses.contains("This is the response from Mock Server 1"));
        assertTrue(responses.contains("This is the response from Mock Server 2"));
        assertTrue(responses.contains("This is the response from Mock Server 3"));
    }

        @After
    public void tearDown() throws Exception {
        // Stop the servers
        // You need to implement a method to stop the servers
        MockServer1.stopServer();
        MockServer2.stopServer();
        MockServer3.stopServer();
    }
}
