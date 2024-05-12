package StrategyPattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class RandomStrategy implements LoadBalancingStrategy {
    private Random random = new Random();

    @Override
    public String getServer(ConcurrentHashMap<String, Boolean> servers) {
        List<String> availableServers = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : servers.entrySet()) {
            if (entry.getValue()) {
                availableServers.add(entry.getKey());
            }
        }
        return availableServers.get(random.nextInt(availableServers.size()));
    }
}