package net.earthmc.emcapiclient.manager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.earthmc.emcapiclient.exception.APIUnavailableException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.UUID;

public class RequestManager {
    private final OkHttpClient client;

    public RequestManager() {
        client = new OkHttpClient();
    }

    /**
     * This method will automatically append random text to the end of the url to bypass CloudFlare caching
     *
     * @param url URL as a string
     * @return The URL's response body
     */
    public String requestURL(String url) throws APIUnavailableException {
        url += url.contains("?") ? "&" : "?";
        url += UUID.randomUUID();

        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            ResponseBody body = response.body();
            return body.string();
        } catch (IOException e) {
            throw new APIUnavailableException();
        }
    }

    /**
     *
     * @param url URL as a string
     * @return The URL as a {@link JsonArray}, null if the URL is offline
     * @throws com.google.gson.JsonSyntaxException If the URL does not return a valid JSON array
     */
    public JsonArray getURLAsJsonArray(String url) throws APIUnavailableException {
        String response = requestURL(url);
        if (response == null) return null;

        Gson gson = new Gson();
        return gson.fromJson(response, JsonArray.class);
    }

    public JsonObject getURLAsJsonObject(String url) throws APIUnavailableException {
        String response = requestURL(url);
        if (response == null) return null;

        Gson gson = new Gson();
        return gson.fromJson(response, JsonObject.class);
    }
}
