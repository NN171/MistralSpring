package com.example.mistral;

import com.example.mistral.config.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

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
            @Qualifier("Busy") VectorStore busyVectorStore,
            @Qualifier("Fav") VectorStore FavVectorStore
    ) {
        System.out.println("Create chat client!");
        return chatClientBuilder
                .defaultSystem(
                        "Ты семейный помощник. Отвечай кратко и по делу. Отвечай на том же языке, на котором пришел запрос."
                )
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(chatMemory, DEFAULT_CHAT_MEMORY_CONVERSATION_ID, 10),
                        new SimpleLoggerAdvisor(),
                        new QuestionAnswerAdvisor(busyVectorStore, SearchRequest.query("Журнал занятости семьи")),
                        new QuestionAnswerAdvisor(FavVectorStore, SearchRequest.query("Интересы семьи"))
                )
                .build();
    }

    @Bean
    @Description("Get price by stock name")
    public Function<String, String> eventsByDateFunction() {
        return key -> "09:00 - 10:30\n" +
                "Зоопарк \"В мире животных\"\n" +
                "Экскурсия с кормлением жирафов и пингвинов\n" +
                "\n" +
                "11:00 - 12:30\n" +
                "Научный музей \"Экспериментариум\"\n" +
                "Интерактивные опыты и эксперименты для всех возрастов\n" +
                "\n" +
                "13:00 - 14:30\n" +
                "Фудкорт \"Вкусный квартал\"\n" +
                "Разнообразные кухни мира под одной крышей\n" +
                "\n" +
                "15:00 - 17:00\n" +
                "Верёвочный парк \"Высотный\"\n" +
                "Трассы разной сложности для детей и взрослых\n" +
                "\n" +
                "17:30 - 19:00\n" +
                "Батутный центр \"Прыг-Скок\"\n" +
                "Свободные прыжки и акробатические программы\n" +
                "\n" +
                "19:30 - 21:00\n" +
                "Квест-комната \"Тайна пирамиды\"\n" +
                "Командное приключение с загадками\n" +
                "\n" +
                "21:30 - 23:00\n" +
                "Вечерний кинопарк\n" +
                "Просмотр фильма под открытым небом\n" +
                "\n" +
                "23:30 - 01:00\n" +
                "Ночная экскурсия \"Тайны города\"\n" +
                "Прогулка по историческим местам с фонариками";
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
