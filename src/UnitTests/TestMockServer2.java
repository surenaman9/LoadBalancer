package UnitTests;

import Servers.MockServer2;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class TestMockServer2 {
    private static MockServer2 server2;


    @Before
    public void setUp() throws Exception {

        server2 = new MockServer2();
        server2.main(new String[]{});


    }

    @Test
    public void TestHandle_Server2_Success() throws IOException {
        URL url = new URL("http://localhost:5002");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        assertEquals(200, responseCode); // Check if the response code is 200

        BufferedReader in = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        conn.disconnect();

        assertEquals("This is the response from Mock Server 2", content.toString());
    }

}
