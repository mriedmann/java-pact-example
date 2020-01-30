package CustomerConsumer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConsumerConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerConsumerApplication.class, args);
    }

    @Bean
    @Autowired
    public CommandLineRunner run(ConsumerRunner runner) throws Exception {
        return runner;
    }
}
