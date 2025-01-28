package au.lupine.emcapiclient.manager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import au.lupine.emcapiclient.object.exception.FailedRequestException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.IntStream;

public class RequestManager {

    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public JsonObject getURIAsJsonObject(@NotNull URI uri) {
        return (JsonObject) getURIAsJsonElement(uri, JsonObject.class);
    }

    public JsonArray getURIAsJsonArray(@NotNull URI uri) {
        return (JsonArray) getURIAsJsonElement(uri, JsonArray.class);
    }

    private <T extends JsonElement> JsonElement getURIAsJsonElement(@NotNull URI uri, @NotNull Class<T> elementClass) {
        try {
            HttpResponse<String> response = client.send(
                    HttpRequest.newBuilder().GET().uri(uri).build(),
                    HttpResponse.BodyHandlers.ofString()
            );

            int statusCode = response.statusCode();
            if (statusCode != 200) throw new FailedRequestException(statusCode);

            return gson.fromJson(response.body(), elementClass);
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }

    public JsonArray batchPostAsJsonArray(@NotNull URI uri, @NotNull List<String> query) {
        List<List<String>> batches = IntStream.range(0, (query.size() + 99) / 100)
                .mapToObj(i -> query.subList(i * 100, Math.min((i + 1) * 100, query.size())))
                .toList();

        List<CompletableFuture<JsonArray>> futures = batches.stream()
                .map(batch -> CompletableFuture.supplyAsync(() ->
                        postURIAsJsonArray(uri, createRequestBody(batch))))
                .toList();

        CompletableFuture<JsonArray> combined = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).thenApply(v -> {
            JsonArray result = new JsonArray();
            futures.forEach(future -> result.addAll(future.join()));

            return result;
        });

        try {
            return combined.join();
        } catch (CompletionException e) {
            if (e.getCause() instanceof FailedRequestException) throw (FailedRequestException) e.getCause();
            throw e;
        }
    }

    public JsonArray postURIAsJsonArray(@NotNull URI uri, @NotNull JsonObject body) {
        return (JsonArray) postURIAsJsonElement(uri, body, JsonArray.class);
    }

    private <T extends JsonElement> JsonElement postURIAsJsonElement(@NotNull URI uri, @NotNull JsonObject body, @NotNull Class<T> elementClass) {
        try {
            HttpResponse<String> response = client.send(
                    HttpRequest.newBuilder().POST(
                            HttpRequest.BodyPublishers.ofString(body.toString())
                    ).uri(uri).build(),
                    HttpResponse.BodyHandlers.ofString()
            );

            int statusCode = response.statusCode();
            if (statusCode != 200) throw new FailedRequestException(statusCode);

            return gson.fromJson(response.body(), elementClass);
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }

    private JsonObject createRequestBody(List<String> query) {
        JsonObject body = new JsonObject();
        JsonArray queryArray = new JsonArray();
        for (String entry : query) {
            queryArray.add(entry);
        }
        body.add("query", queryArray);

        return body;
    }
}
