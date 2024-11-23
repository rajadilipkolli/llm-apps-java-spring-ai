package com.thomasvitale.ai.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private final ChatClient chatClient;

    ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/chat")
    String chat(String question) {
        logger.info("Chatting: {}", question);
        return chatClient
                .prompt(question)
                .call()
                .content();
    }

    @GetMapping("/chat/generic-options")
    String chatGenericOptions(String question) {
        logger.info("Chatting with generic options: {}", question);
        return chatClient
                .prompt(question)
                .options(ChatOptionsBuilder.builder()
                        .withModel(OpenAiApi.ChatModel.GPT_4_O_MINI.getValue())
                        .withTemperature(0.9)
                        .build())
                .call()
                .content();
    }

    @GetMapping("/chat/provider-options")
    String chatProviderOptions(String question) {
        logger.info("Chatting with provider options: {}", question);
        return chatClient
                .prompt(question)
                .options(OpenAiChatOptions.builder()
                        .withLogprobs(true)
                        .build())
                .call()
                .content();
    }

    @GetMapping("/chat/stream")
    Flux<String> chatStream(String question) {
        logger.info("Chatting with stream: {}", question);
        return chatClient
                .prompt(question)
                .stream()
                .content();
    }

    @GetMapping("/chat/functions")
    String chatFunctions(String authorName) {
        logger.info("Chatting with functions: {}", authorName);
        var userPromptTemplate = "What books written by {author} are available in the library?";
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(userPromptTemplate)
                        .param("author", authorName)
                )
                .functions("booksByAuthor", "bestsellerBookByAuthor")
                .call()
                .content();
    }

}