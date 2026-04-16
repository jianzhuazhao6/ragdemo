package com.zjh.research.ragdemo.service;

import com.zjh.research.ragdemo.dto.ChatDto;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatService {

    ChatClient chatClient;
    final ChatClient.Builder chatClientBuilder;

    @PostConstruct
    public void init() {
        chatClient = chatClientBuilder.build();
    }

    public Mono<String> chat(Mono<ChatDto> chatDtoMono) {
        return chatDtoMono.flatMap(chatDto ->
                Mono.fromCallable(() -> chatClient.prompt()
                                .user(spec -> spec.text(chatDto.getUserPrompt()))
                                .system(spec -> spec.text(chatDto.getSystemPrompt()))
                                .call()
                                .chatResponse().getResult().getOutput().getText())
                        .subscribeOn(Schedulers.boundedElastic())
                        .doOnNext(System.out::println)
        );
    }
}
