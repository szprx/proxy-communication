package com.dglazewski.proxycommunication.resttemplate;


import com.dglazewski.proxycommunication.config.ConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Configuration
@Slf4j
public class RestTemplateConfiguration {
    @Bean
    public RestTemplate restTemplate(ConfigProperties configProperties){
        SimpleClientHttpRequestFactory clientHttpReq = new SimpleClientHttpRequestFactory();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(configProperties.getHost(), configProperties.getPort()));
        clientHttpReq.setProxy(proxy);

        RestTemplate restTemplate = new RestTemplate(clientHttpReq);
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(configProperties.getUsername(), configProperties.getPassword()));

        return restTemplate;
    }
}
