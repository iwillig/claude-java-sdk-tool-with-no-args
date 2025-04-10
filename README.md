# Anthropic Java SDK Tool Error example.

This is an example of an error we are seeing when we define a tool without any arguments.

## Context

This project shows an example of an error when you define a Tool
without any arguments. This is due to the fact that the Java
implementation seems to be assuming that a tool will always be defined
with an argument.

### Tool example

Given a tool that looks like this,

```java
var schema = Tool.InputSchema.builder()
    .build();

var tool = Tool.builder()
    .name("get_current_time")
    .inputSchema(schema)
    .description("This returns the current time.")
    .build();
```

And usage like this,

```java
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
}).onCompleteFuture().whenComplete((unused, error) -> {
    System.out.println(error);
}).join();
```

### Example error

```java

com.fasterxml.jackson.databind.exc.MismatchedInputException: No content to map due to end-of-input
 at [Source: REDACTED (`StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION` disabled); line: 1]
Error using Anthropic API: com.fasterxml.jackson.databind.exc.MismatchedInputException: No content to map due to end-of-input
 at [Source: REDACTED (`StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION` disabled); line: 1]
java.util.concurrent.CompletionException: com.fasterxml.jackson.databind.exc.MismatchedInputException: No content to map due to end-of-input
 at [Source: REDACTED (`StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION` disabled); line: 1]
	at java.base/java.util.concurrent.CompletableFuture.wrapInCompletionException(CompletableFuture.java:323)
	at java.base/java.util.concurrent.CompletableFuture.encodeThrowable(CompletableFuture.java:376)
	at java.base/java.util.concurrent.CompletableFuture.completeThrowable(CompletableFuture.java:391)
	at java.base/java.util.concurrent.CompletableFuture.uniWhenComplete(CompletableFuture.java:918)
	at java.base/java.util.concurrent.CompletableFuture$UniWhenComplete.tryFire(CompletableFuture.java:885)
	at java.base/java.util.concurrent.CompletableFuture.postComplete(CompletableFuture.java:554)
	at java.base/java.util.concurrent.CompletableFuture.completeExceptionally(CompletableFuture.java:2238)
	at com.anthropic.core.http.AsyncStreamResponseKt$toAsync$1$subscribe$1$2.invoke(AsyncStreamResponse.kt:125)
	at com.anthropic.core.http.AsyncStreamResponseKt$toAsync$1$subscribe$1$2.invoke(AsyncStreamResponse.kt:94)
	at com.anthropic.core.http.AsyncStreamResponseKt$toAsync$1.subscribe$lambda$3$lambda$2(AsyncStreamResponse.kt:95)
	at java.base/java.util.concurrent.CompletableFuture.uniWhenComplete(CompletableFuture.java:907)
	at java.base/java.util.concurrent.CompletableFuture$UniWhenComplete.tryFire(CompletableFuture.java:885)
	at java.base/java.util.concurrent.CompletableFuture$Completion.run(CompletableFuture.java:526)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642)
	at java.base/java.lang.Thread.run(Thread.java:1575)
Caused by: com.fasterxml.jackson.databind.exc.MismatchedInputException: No content to map due to end-of-input
 at [Source: REDACTED (`StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION` disabled); line: 1]
	at com.fasterxml.jackson.databind.exc.MismatchedInputException.from(MismatchedInputException.java:59)
	at com.fasterxml.jackson.databind.ObjectMapper._initForReading(ObjectMapper.java:5008)
	at com.fasterxml.jackson.databind.ObjectMapper._readMapAndClose(ObjectMapper.java:4910)
	at com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:3860)
	at com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:3828)
	at com.anthropic.helpers.MessageAccumulator$accumulate$1.visitContentBlockStop(MessageAccumulator.kt:333)
	at com.anthropic.helpers.MessageAccumulator$accumulate$1.visitContentBlockStop(MessageAccumulator.kt:174)
	at com.anthropic.models.messages.RawMessageStreamEvent.accept(RawMessageStreamEvent.kt:85)
	at com.anthropic.helpers.MessageAccumulator.accumulate(MessageAccumulator.kt:173)
	at com.example.App.lambda$main$0(App.java:58)
	at com.anthropic.core.http.TrackedHandler.onNext(PhantomReachableClosingAsyncStreamResponse.kt:53)
	at com.anthropic.core.http.AsyncStreamResponseKt$toAsync$1$subscribe$1$2$1.invoke(AsyncStreamResponse.kt:110)
	at com.anthropic.core.http.AsyncStreamResponseKt$toAsync$1$subscribe$1$2$1.invoke(AsyncStreamResponse.kt:110)
	at com.anthropic.core.http.AsyncStreamResponseKt$toAsync$1$subscribe$1$2.invoke$lambda$0(AsyncStreamResponse.kt:110)
	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
	at java.base/java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:215)
	at java.base/java.util.Iterator.forEachRemaining(Iterator.java:133)
	at java.base/java.util.Spliterators$IteratorSpliterator.forEachRemaining(Spliterators.java:1939)
	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:570)
	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:560)
	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:151)
	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:174)
	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:265)
	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:636)
	at com.anthropic.core.http.AsyncStreamResponseKt$toAsync$1$subscribe$1$2.invoke(AsyncStreamResponse.kt:110)
	... 8 more
```

## Prerequisites

- Java 17 or higher
- Maven
- Anthropic API Key

## Setup

1. Set your Anthropic API key as an environment variable:

```bash
export ANTHROPIC_API_KEY=your_api_key_here
```

## Building the Project

```bash
mvn clean compile
```

## Running the Application

```bash
 mvn exec:java -Dexec.mainClass="com.example.App"
```

## Project Structure

- `src/main/java/com/example/App.java`: Main application that demonstrates how to use the Anthropic Java SDK
- `pom.xml`: Maven configuration file with dependencies
