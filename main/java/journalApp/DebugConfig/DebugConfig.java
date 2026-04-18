package journalApp.DebugConfig;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DebugConfig {

    @Value("${spring.data.mongodb.uri:NOT_FOUND}")
    private String uri;

    @PostConstruct
    public void check() {
        System.out.println("SPRING URI: " + uri);
    }
}