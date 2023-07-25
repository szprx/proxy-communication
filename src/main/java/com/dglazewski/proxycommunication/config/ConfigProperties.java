package com.dglazewski.proxycommunication.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "proxy")
public class ConfigProperties {
    private String host;
    private Integer port;
    private String username;
    private String password;
    private final String dogFactsUrl = "https://dog-api.kinduff.com/api/facts?number=5";

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        System.setProperty("java.net.useSystemProxies", "true");
        // http
        System.setProperty("http.proxyHost", host);
        System.setProperty("http.proxyPort", String.valueOf(port));
        System.setProperty("http.proxyUser", username);
        System.setProperty("http.proxyPassword", password);

        // https
        System.setProperty("https.proxyHost", host);
        System.setProperty("https.proxyPort", String.valueOf(port));
        System.setProperty("https.proxyUser", username);
        System.setProperty("https.proxyPassword", password);

        System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "false");
        System.setProperty("jdk.https.auth.tunneling.disabledSchemes", "false");

        Authenticator.setDefault(new Authenticator() {
                                     @Override
                                     public PasswordAuthentication getPasswordAuthentication() {
                                         return new PasswordAuthentication(username, password.toCharArray());
                                     }
                                 }
        );
    }
}
