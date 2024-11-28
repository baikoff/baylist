package org.baylist.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {

    @Bean
    public RestClient todoistRestClient(@Value("${todoist.baseUrl}") String url,
                                        @Value("${todoist.token}") String token) {
        return RestClient
                .builder()
                .baseUrl(url)
                .defaultHeaders(h -> h.add("Authorization", "Bearer " + token))
                .build();
    }


}
