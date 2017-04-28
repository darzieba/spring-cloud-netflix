package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@EnableDiscoveryClient
@SpringBootApplication
public class EurekaConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaConsumerApplication.class, args);
    }
}

@RestController
class ServiceInstanceRestController {

    private static final String EUREKA_PRODUCER_SERVICE_NAME = "eureka-producer";

    @Value("${eureka.instance.instance-id}")
    private String instanceId;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }

    @RequestMapping("/info")
    public String info() {
        return "Client " + instanceId;
    }

    @RequestMapping("/producer")
    public String producer() {
        return "Client " + instanceId + " connected to " + fetchInfoFromProducer().getBody();
    }

    private ResponseEntity<String> fetchInfoFromProducer() {
        return restTemplate.getForEntity("http://" + EUREKA_PRODUCER_SERVICE_NAME + "/info", String.class);
    }
}