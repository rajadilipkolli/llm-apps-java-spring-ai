package com.thomasvitale.ai.spring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "60s")
class ChatModelsMultipleProvidersApplicationTests {

    @Autowired
    WebTestClient webTestClient;

    @Test
    @EnabledIfEnvironmentVariable(named = "SPRING_AI_MISTRALAI_API_KEY", matches = ".*")
    void chatMistralAI() {
        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/chat/mistral")
                        .queryParam("message", "What is the capital of Italy?")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(result -> {
                    assertThat(result).containsIgnoringCase("Rome");
                });
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "SPRING_AI_OPENAI_API_KEY", matches = ".*")
    void chatOpenAI() {
        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/chat/openai")
                        .queryParam("message", "What is the capital of Italy?")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).value(result -> {
                    assertThat(result).containsIgnoringCase("Rome");
                });
    }

}
