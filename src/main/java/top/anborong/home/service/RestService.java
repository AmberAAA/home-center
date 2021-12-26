package top.anborong.home.service;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {

    public final RestTemplate restTemplate;

    public RestService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }
}
