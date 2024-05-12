package UnitTests;

import Servers.MockServer1;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class TestMockServer1 {
    private static MockServer1 server1;




    @Before
    public void setUp() throws Exception {
        server1 = new MockServer1();
        server1.main(new String[]{});

    }

    @Test
    public void TestHandle_Server1_Success() throws IOException {
        URL url = new URL("http://localhost:5001");
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

        assertEquals("This is the response from Mock Server 1", content.toString());
    }



}
