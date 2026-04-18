package journalApp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {

    public static void main(String[] args) {

        System.out.println("USERNAME: " + System.getenv("MONGO_USERNAME"));
        System.out.println("PASSWORD: " + System.getenv("MONGO_PASSWORD"));

        SpringApplication.run(JournalApplication.class, args);
    }

    @Bean //Like a Translator between MongoDB and Spring Boot
    public PlatformTransactionManager add(MongoDatabaseFactory dbFactory){
        return new MongoTransactionManager((dbFactory));
    }

}