package com.dglazewski.proxycommunication.resttemplate;

import com.dglazewski.proxycommunication.config.ConfigProperties;
import com.dglazewski.proxycommunication.model.DogRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RestController
@RequestMapping("resttemplate")
@RequiredArgsConstructor
public class RestTemplateController {
    private final RestTemplate restTemplate;
    private final ConfigProperties configProperties;

    @GetMapping("/getFacts")
    public String getDogFacts() {
        return Objects.requireNonNull(restTemplate.getForEntity(
                        configProperties.getDogFactsUrl(), DogRequest.class)
                .getBody()).getFacts().toString();
    }
}
