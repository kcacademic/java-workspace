package com.kchandrakant;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.openai.OpenAiChatModel;

import java.util.HashMap;
import java.util.Map;

import static dev.langchain4j.model.openai.OpenAiModelName.GPT_3_5_TURBO;
import static java.time.Duration.ofSeconds;

public class PromptTemplates {

    public static void main(String[] args) {

        // Create a prompt template
        PromptTemplate promptTemplate = PromptTemplate.from("Tell me a {{adjective}} joke about {{content}}..");

        // Generate prompt using the prompt template and user variables
        Map<String, Object> variables = new HashMap<>();
        variables.put("adjective", "funny");
        variables.put("content", "humans");
        Prompt prompt = promptTemplate.apply(variables);

        System.out.println(prompt.text());

        // Create an instance of a model
        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(ApiKeys.OPENAI_API_KEY)
                .modelName(GPT_3_5_TURBO)
                .temperature(0.3)
                .build();

        // Start interacting
        String response = model.generate(prompt.text());

        System.out.println(response);

    }
}