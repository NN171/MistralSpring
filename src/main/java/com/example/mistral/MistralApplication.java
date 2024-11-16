package com.example.mistral;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MistralApplication {

    public static void main(String[] args) {
        SpringApplication.run(MistralApplication.class, args);
    }

    @Bean
    ChatClient provideChatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder
                .defaultSystem(
                        "Ты семейный помощник. Отвечай кратко и по делу. Отвечай на том же языке, на котором пришел запрос."
                )
                .build();
    }

//    @Bean
//    CommandLineRunner runner(ChatClient chatClient) {
//        return args -> {
//            System.out.println("Request send");
//
//            var response = chatClient.prompt()
//                    .user("Расскажи подробнее о себе")
//                    .call()
//                    .content();
//
//            System.out.println(response);
//        };
//    }
}
