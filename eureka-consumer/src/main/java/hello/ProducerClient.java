package hello;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "eureka-producer", fallback = HystrixClientFallback.class)
public interface ProducerClient {

    @RequestMapping(method = RequestMethod.GET, value = "/info")
    String info();
}

class HystrixClientFallback implements ProducerClient {

    @Override
    public String info() {
        return "Fallback";
    }
}