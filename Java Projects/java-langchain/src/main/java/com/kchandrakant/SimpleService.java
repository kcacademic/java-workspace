package com.kchandrakant;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

public class SimpleService {

    interface Assistant {

        String chat(String message);
    }

    public static void main(String[] args) {

        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

        Assistant assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(OpenAiChatModel.withApiKey(ApiKeys.OPENAI_API_KEY))
                .chatMemory(chatMemory)
                .build();

        String answer = assistant.chat("Hello! My name is Klaus.");
        System.out.println(answer); // Hello Klaus! How can I assist you today?

        String answerWithName = assistant.chat("What is my name?");
        System.out.println(answerWithName); // Your name is Klaus.
    }
}
