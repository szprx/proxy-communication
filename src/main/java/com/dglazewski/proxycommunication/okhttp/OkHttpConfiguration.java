package com.dglazewski.proxycommunication.okhttp;

import com.dglazewski.proxycommunication.config.ConfigProperties;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class OkHttpConfiguration {

    @Bean
    public OkHttpClient okHttpClient(ConfigProperties configProperties) {

        Authenticator proxyAuthenticator = (route, response) -> {
            String credential = Credentials.basic(
                    configProperties.getUsername(), configProperties.getPassword());
            return response.request().newBuilder()
                    .header("Proxy-Authorization", credential)
                    .build();
        };

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        logging.redactHeader("Authorization");

        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(configProperties.getHost(), configProperties.getPort())))
                .proxyAuthenticator(proxyAuthenticator)
                .addInterceptor(logging)
                .build();

    }

}
