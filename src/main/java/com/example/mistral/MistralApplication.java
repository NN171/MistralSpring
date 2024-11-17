package com.example.mistral;

import com.example.mistral.config.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.DEFAULT_CHAT_MEMORY_CONVERSATION_ID;

@SpringBootApplication
public class MistralApplication {

    public static void main(String[] args) {
        SpringApplication.run(MistralApplication.class, args);
    }

    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }

    @Bean
    ChatClient provideChatClient(
            ChatClient.Builder chatClientBuilder,
            ChatMemory chatMemory,
            VectorStore vectorStore
    ) {
        System.out.println("Create chat client!!!!!!!!!!");
        return chatClientBuilder
                .defaultSystem(
                        "Ты семейный помощник. Отвечай кратко и по делу. Отвечай на том же языке, на котором пришел запрос."
                )
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(chatMemory, DEFAULT_CHAT_MEMORY_CONVERSATION_ID, 10),
                        new SimpleLoggerAdvisor(),
                        new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults())
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
