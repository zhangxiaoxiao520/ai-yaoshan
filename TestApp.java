import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestApp {
    public static void main(String[] args) {
        try {
            SpringApplication.run(com.example.yaoshan.YaoshanApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}