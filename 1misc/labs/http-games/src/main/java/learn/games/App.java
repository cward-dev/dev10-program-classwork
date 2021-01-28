package learn.games;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// 1 This single annotation is incredibly powerful. It encompasses all configuration behavior for our Spring Boot application.
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args); // 2 we call SpringApplication.run and provide our App class as a configuration source.
    }
}
