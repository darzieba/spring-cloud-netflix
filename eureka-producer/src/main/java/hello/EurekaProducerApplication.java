package hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
public class EurekaProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaProducerApplication.class, args);
    }
}

@RestController
class ServiceInstanceRestController {

    @Value("${eureka.instance.instance-id}")
    private String instanceId;

    @RequestMapping("/info")
    public String info() {
        return "Producer " + instanceId;
    }
}