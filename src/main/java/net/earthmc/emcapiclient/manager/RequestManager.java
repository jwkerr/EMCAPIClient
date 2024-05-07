package net.earthmc.emcapiclient.manager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.earthmc.emcapiclient.exception.BadRequestException;
import net.earthmc.emcapiclient.exception.GatewayTimeoutException;
import net.earthmc.emcapiclient.exception.NotFoundException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.UUID;

public class RequestManager {

    private final OkHttpClient client = new OkHttpClient();

    /**
     * This method will automatically append random text to the end of the url to bypass CloudFlare caching
     *
     * @param url URL as a string
     * @return The URL's response body as a string
     */
    public String requestURL(String url) {
        url += url.contains("?") ? "&" : "?";
        url += UUID.randomUUID();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String message = response.message();
            switch (response.code()) {
                case 400 -> throw new BadRequestException(message);
                case 404 -> throw new NotFoundException(message);
                case 504 -> throw new GatewayTimeoutException(message);
            }

            ResponseBody body = response.body();
            if (body == null) return null;

            return body.string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     *
     * @param url URL as a string
     * @return The URL parsed to a {@link JsonArray}, null if the URL has no response body
     * @throws com.google.gson.JsonSyntaxException If the URL does not return a valid JSON array
     */
    public JsonArray getURLAsJsonArray(String url) {
        String response = requestURL(url);
        if (response == null) return null;

        Gson gson = new Gson();
        return gson.fromJson(response, JsonArray.class);
    }

    /**
     *
     * @param url URL as a string
     * @return The URL parsed to a {@link JsonObject}, null if the URL has no response body
     * @throws com.google.gson.JsonSyntaxException If the URL does not return a valid JSON object
     */
    public JsonObject getURLAsJsonObject(String url) {
        String response = requestURL(url);
        if (response == null) return null;

        Gson gson = new Gson();
        return gson.fromJson(response, JsonObject.class);
    }
}
