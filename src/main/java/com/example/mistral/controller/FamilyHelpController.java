package com.example.mistral.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/family")
public class FamilyHelpController {

    @Value("classpath:/static/family/HelpForWorkSystem.txt")
    private Resource familyBusySystem;

    @Value("classpath:/static/family/ActivityPlanSystem.txt")
    private Resource familyFavoritesSystem;

    @Value("classpath:/static/family/PresentSystem.txt")
    private Resource familyPresentsSystem;

    @Value("classpath:/static/family/HelpFamily.txt")
    private Resource familyHelpSystem;

    @Value("classpath:/static/family/EventSystem.txt")
    private Resource familyEventSystem;

    @Autowired
    private ChatClient chatClient;

    @GetMapping("/help/work")
    String helpWithHomeWork(String prompt) {
        System.out.println("helpWithWork called. prompt: " + prompt);

        var response = chatClient.prompt()
                .system(familyBusySystem)
                .user(prompt)
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getContent();

        System.out.println("Response: " + response);
        return response;
    }

    @GetMapping("/activity")
    String activityPlan(String prompt) {
        System.out.println("activityPlan called. prompt: " + prompt);

        var response = chatClient.prompt()
                .system(familyFavoritesSystem)
                .user(prompt)
                .functions("eventsByDateFunction")
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getContent();

        System.out.println("Response: " + response);
        return response;
    }

    @GetMapping("/help")
    String helpFamily() {
        System.out.println("helpFamily called");

        var response = chatClient.prompt()
                .system(familyHelpSystem)
                .user("Кому нужно помочь из моей семьи?")
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getContent();

        System.out.println("Response: " + response);
        return response;
    }

    @GetMapping("/event")
    String eventFamily() {
        System.out.println("eventFamily called");

        var response = chatClient.prompt()
                .system(familyEventSystem)
                .user("Какие общие события ждут нас в будущем. Напомни когда они и будут ли")
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getContent();

        System.out.println("Response: " + response);
        return response;
    }

    @GetMapping("/help/gift")
    String giftFamily(String prompt) {
        System.out.println("giftFamily called. prompt: " + prompt);

        var response = chatClient.prompt()
                .system(familyPresentsSystem)
                .user(prompt)
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getContent();

        System.out.println("Response: " + response);
        return response;
    }
}
