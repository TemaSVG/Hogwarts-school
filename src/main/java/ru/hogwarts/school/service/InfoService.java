package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InfoService {
    
    @Value("${server.port}")
    private String port;

    public String getPort() {
        return port;
    }

    public int getSlowSum() {
        return java.util.stream.Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, Integer::sum);
    }

    public int getFastSum() {
        int n = 1_000_000;
        return n * (n + 1) / 2;
    }
    
    
}