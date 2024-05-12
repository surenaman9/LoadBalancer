package StrategyPattern;

import java.util.concurrent.ConcurrentHashMap;

public interface LoadBalancingStrategy {
    String getServer(ConcurrentHashMap<String, Boolean> servers);
}
