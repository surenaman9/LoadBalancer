package UnitTests;

import Servers.MockServer3;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class TestMockServer3 {
    private static MockServer3 server3;


    @Before
    public void setUp() throws Exception {

        server3 = new MockServer3();
        server3.main(new String[]{});


    }

    @Test
    public void TestHandle_Server2_Success() throws IOException {
        URL url = new URL("http://localhost:5003");
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

        assertEquals("This is the response from Mock Server 3", content.toString());
    }

}
