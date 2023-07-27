package com.dglazewski.proxycommunication.okhttp;

import com.dglazewski.proxycommunication.config.ConfigProperties;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("okhttp")
@RequiredArgsConstructor
public class OkHttpController {

    private final ConfigProperties configProperties;
    private final OkHttpClient okHttpClient;

    @GetMapping("/getFacts")
    public String getDogFacts() {
        Request request = new Request.Builder()
                .url(configProperties.getDogFactsUrl())
                .build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            assert response.body() != null;
            return response.body().string();
        } catch (IOException e) {
        return "IOException occurred.";
        }
    }
}
