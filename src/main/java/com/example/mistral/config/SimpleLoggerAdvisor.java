package com.example.mistral.config;

import org.springframework.ai.chat.client.advisor.api.*;
import org.springframework.ai.chat.model.MessageAggregator;
import reactor.core.publisher.Flux;

public class SimpleLoggerAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        var model = advisedRequest.chatOptions().getModel();
        var maxTokens = advisedRequest.chatOptions().getMaxTokens();
        var temperture = advisedRequest.chatOptions().getTemperature();
        System.out.println("model: " + model +
                "\n maxTokens: " + maxTokens +
                "\n temperture: " + temperture);
        System.out.println("aroundCall options:" + advisedRequest);

        AdvisedResponse advisedResponse = chain.nextAroundCall(advisedRequest);

        System.out.println("AFTER: {}" + advisedResponse);

        return advisedResponse;
    }

    @Override
    public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {

        System.out.println("BEFORE: {}" + advisedRequest);

        Flux<AdvisedResponse> advisedResponses = chain.nextAroundStream(advisedRequest);

        return new MessageAggregator().aggregateAdvisedResponse(advisedResponses,
                advisedResponse -> System.out.println("AFTER: {}" + advisedResponse));
    }
}
