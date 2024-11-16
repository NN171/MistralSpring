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

    @GetMapping("/help")
    String familyHelp(String prompt) {
        System.out.println("familyHelp called. prompt: " + prompt);
//        return "Test successful";
        return chatClient.prompt()
                .user(prompt)
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getContent();
    }
}
