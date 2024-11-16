package com.example.mistral.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/family")
public class FamilyHelpController {

    @Autowired
    private ChatClient chatClient;

    @GetMapping("/help/work")
    String helpWithWork(String prompt) {
        chatClient.prompt()
                .system("Обьясни ответ пошагово")
                .user("");
        System.out.println("helpWithWork called. prompt: " + prompt);

        return chatClient.prompt()
                .user(prompt)
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getContent();
    }

    @GetMapping("/activity")
    String activityPlan(String prompt) {
        System.out.println(chatClient.prompt()
                .system("")
                .user("")
                .call().content());
        System.out.println("activityPlan called. prompt: " + prompt);

        return chatClient.prompt()
                .user(prompt)
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getContent();
    }

    @GetMapping("/help")
    String helpFamily(String prompt) {
        System.out.println("helpFamily called. prompt: " + prompt);

        return chatClient.prompt()
                .user(prompt)
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getContent();
    }
}
