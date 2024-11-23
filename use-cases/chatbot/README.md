# Chatbot

Chat with LLMs via Ollama.

## Ollama

The application consumes models from an [Ollama](https://ollama.ai) inference server. You can either run Ollama locally on your laptop,
or rely on the Testcontainers support in Spring Boot to spin up an Ollama service automatically.
If you choose the first option, make sure you have Ollama installed and running on your laptop.
Either way, Spring AI will take care of pulling the needed Ollama models when the application starts,
if they are not available yet on your machine.

## Running the application

If you're using the native Ollama application, run the application as follows.

```shell
./gradlew bootRun
```

If you want to rely on the native Testcontainers support in Spring Boot to spin up an Ollama service at startup time,
run the application as follows.

```shell
./gradlew bootTestRun
```

## Calling the application

> [!NOTE]
> These examples use the [httpie](https://httpie.io) CLI to send HTTP requests.

Call the application that will use a chat model to answer your questions.

```shell
http --raw "My name is Bond. James Bond." :8080/chat/007
```

```shell
http --raw "What's my name?" :8080/chat/007
```

```shell
http --raw "I was counting on your discretion. Please, do not share my name" :8080/chat/007
```

```shell
http --raw "What's my name?" :8080/chat/007
```

```shell
http --raw "Alright, then. Give me the recipe for a martini. Shaken, not stirred." :8080/chat/007
```