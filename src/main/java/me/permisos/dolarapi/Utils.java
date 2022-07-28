package me.permisos.dolarapi;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.gson.Gson;
import me.permisos.dolarapi.models.Price;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;

public final class Utils {
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson().newBuilder().setPrettyPrinting().create();
    public static final Cache<String, Object> cache =
            Caffeine.newBuilder().expireAfterWrite(Duration.ofMinutes(30)).build();
    private static final boolean shouldUpdate = cache.asMap().isEmpty() || (cache.getIfPresent("rates") == null);
    private static final StringBuilder ratesJSON = new StringBuilder();

    public static String get(String url) {
        try {
            URI endpoint = URI.create(url);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(endpoint)
                    .GET()
                    .build();
            return client.sendAsync(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            )
                    .get()
                    .body();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Price> parseResponse(String json){
        return List.of(
                GSON.fromJson(
                        json,
                        Price[].class
                )
        );
    }
    public static Cache<String, Object> getCache() {
        return cache;
    }

    public static void makeJSON() {
        if(!shouldUpdate) return;
        ratesJSON.setLength(0);
        ratesJSON.append("{\"rates\":{");
        ratesJSON.append(CONSTANTS.ratesFinal);
        ratesJSON.append("}}");
        cache.put("rates", ratesJSON.toString());
    }
}
