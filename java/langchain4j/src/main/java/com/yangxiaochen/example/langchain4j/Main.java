package com.yangxiaochen.example.langchain4j;

import dev.langchain4j.model.openai.OpenAiChatModel;

import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;

public class Main {

    public static void main(String[] args) {

        String apiKey = "demo";

        OpenAiChatModel model = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName(GPT_4_O_MINI)
                .build();


        String answer = model.generate("Hello World, how are you?");
        System.out.println(answer); // Hello World
    }
}
