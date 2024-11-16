package com.example.mistral;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.internal.Function;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;

@SpringBootApplication
public class MistralApplication {

    public static void main(String[] args) {
        SpringApplication.run(MistralApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(ChatClient.Builder chatClientBuilder) {
        return args -> {
            var chatClient = chatClientBuilder.build();
            System.out.println("Request send");

            chatClient.prompt()
                            .user("What is the weather in Amsterdam and Paris?")
                                    .stream()
                                            .chatResponse().subscribe((c) -> {
                        System.out.print(c.getResult().getOutput().getContent());
                    });

//            var response = chatClient.prompt()
//                    .user("What is the weather in Amsterdam and Paris?")
//                    .call()
//                    .content();

//            System.out.println(response);
        };
    }
}
