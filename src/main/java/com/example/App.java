package com.example;

import com.anthropic.client.AnthropicClientAsync;
import com.anthropic.client.okhttp.AnthropicOkHttpClientAsync;
import com.anthropic.core.JsonValue;
import com.anthropic.helpers.MessageAccumulator;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.helpers.MessageAccumulator;
import com.anthropic.models.messages.Tool;
import com.anthropic.models.messages.Tool.InputSchema;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class App {
        public static void main(String[] args) {

            var schema = Tool.InputSchema.builder()
                    .build();

            var tool = Tool.builder()
                    .name("get_current_time")
                    .inputSchema(schema)
                    .description("This returns the current time.")
                    .build();

            var apiKey = System.getenv("ANTHROPIC_API_KEY");

            if (apiKey == null || apiKey.isEmpty()) {
                System.err.println("Error: ANTHROPIC_API_KEY environment variable is required");
                System.exit(1);
            }

            try {
                // Initialize the tool

                // Initialize the Anthropic client
                AnthropicClientAsync client = AnthropicOkHttpClientAsync.builder()
                        .apiKey(apiKey)
                        .build();

                MessageAccumulator accumulator = MessageAccumulator.create();

                // Create message with tool
                MessageCreateParams params = MessageCreateParams.builder()
                        .model("claude-3-opus-20240229")
                        .maxTokens(1000)
                        .addTool(tool)
                        .system("You are a helpful AI assistant. When asked about the time, use the get_current_time tool.")
                        .addUserMessage("What is the current time?")
                        .build();


                // Process the response and handle tool usage
                client.messages().createStreaming(params).subscribe(event -> {
                    accumulator.accumulate(event).contentBlockDelta();
                }).onCompleteFuture().
                        whenComplete((unused, error) -> {
                            System.out.println(error);
                        }).
                        join();


            } catch (Exception e) {
                System.err.println("Error using Anthropic API: " + e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }
        }
}
