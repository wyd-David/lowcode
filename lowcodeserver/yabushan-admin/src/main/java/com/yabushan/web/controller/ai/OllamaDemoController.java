/*
package com.yabushan.web.controller.ai;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OllamaDemoController {

    @Autowired
    private OllamaChatClient ollamaChatClient;

    @GetMapping(value = "/chat_1")
    public String chat_1(@RequestParam(value = "message") String message) {
        String call = ollamaChatClient.call(message);
        System.out.println("模型回答 = " + call);
        return call;
    }

    @GetMapping(value = "/chat_2")
    public Object chat_2(@RequestParam(value = "message") String message) {
        Prompt prompt = new Prompt(
                message,
                OllamaOptions.create()
                        //代码中配置，会覆盖application.yml中的配置
                        .withModel("gemma:7b") //使用什么大模型
                        .withTemperature(0.9F) //温度高，更发散,准确性降低，温度低，更保守，准确性高
        );

        ChatResponse call = ollamaChatClient.call(prompt);
        AssistantMessage output = call.getResult().getOutput();
        System.out.println("模型回答 = " + output.getContent());
        return output;
    }

    @GetMapping("/chat_3/{size}")
    public String chatYear(@PathVariable("size") Integer size) {
        String message = "随便写一句话,{size} 字以内";
        PromptTemplate promptTemplate = new PromptTemplate(message);
        promptTemplate.add("size", size);
        System.out.println(promptTemplate.render());
        return ollamaChatClient.call(promptTemplate.render());
    }
}*/
