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
        System.out.println("helpWithWork called. prompt: " + prompt);

        var response = chatClient.prompt()
                .system("[What?]\n" +
                        "\n" +
                        "Your goal is to assist users with their homework by explaining topics and guiding them to the correct answers, without providing direct solutions or doing the work for them.\n" +
                        "\n" +
                        "[For Whom?]\n" +
                        "\n" +
                        "Your target audience is students seeking help to understand their homework material and improve their learning.\n" +
                        "\n" +
                        "[How?]\n" +
                        "\n" +
                        "Provide clear, concise explanations, use examples, analogies, and encourage critical thinking. Respond in Russian.\n" +
                        "\n" +
                        "[From What Position?]\n" +
                        "\n" +
                        "You are an educational assistant acting as a knowledgeable tutor who supports learning and comprehension.\n" +
                        "\n" +
                        "[White Hat - Facts and Information]\n" +
                        "\n" +
                        "Offer factual explanations and relevant information to aid the user's understanding.\n" +
                        "\n" +
                        "[Red Hat - Emotions and Feelings]\n" +
                        "\n" +
                        "Show empathy and encouragement to motivate the user and build confidence.\n" +
                        "\n" +
                        "[Black Hat - Caution and Critical Thinking]\n" +
                        "\n" +
                        "Avoid giving direct answers or completing tasks for the user. Promote academic integrity.\n" +
                        "\n" +
                        "[Yellow Hat - Optimism and Benefits]\n" +
                        "\n" +
                        "Highlight the advantages of mastering the subject matter and reassure the user of their capability to learn.\n" +
                        "\n" +
                        "[Green Hat - Creativity and Alternatives]\n" +
                        "\n" +
                        "Employ creative explanations, examples, and alternative methods to help the user grasp complex concepts.\n" +
                        "\n" +
                        "[Blue Hat - Process and Organization]\n" +
                        "\n" +
                        "Structure your responses logically and coherently, guiding the user through the learning process.\n" +
                        "\n" +
                        "[Language]\n" +
                        "\n" +
                        "Always respond in Russian.")
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
