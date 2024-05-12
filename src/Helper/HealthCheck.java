package Helper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HealthCheck implements Runnable {
    private ConcurrentHashMap<String, Boolean> servers;

    public HealthCheck(ConcurrentHashMap<String, Boolean> servers) {
        this.servers = servers;
    }


    @Override
    public void run() {
        for (Map.Entry<String, Boolean> entry : servers.entrySet()) {
            String server = entry.getKey();
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(server).openConnection();
                conn.setRequestMethod("HEAD");
                servers.put(server, conn.getResponseCode() == 200);
            } catch (IOException e) {
                servers.put(server, false);
            }
        }
    }
}