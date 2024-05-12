package StrategyPattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RoundRobinStrategy implements LoadBalancingStrategy {
    private int serverIndex = 0;

    @Override
    public String getServer(ConcurrentHashMap<String, Boolean> servers) {
        List<String> availableServers = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : servers.entrySet()) {
            if (entry.getValue()) {
                availableServers.add(entry.getKey());
            }
        }
        String server = availableServers.get(serverIndex);
        serverIndex = (serverIndex + 1) % availableServers.size();
        return server;
    }
}