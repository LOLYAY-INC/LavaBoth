// Full Path: src/io/lolyay/jlavalink/v4/rest/LavaLinkRestClient.java
package io.lolyay.jlavalink.v4.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.lolyay.jlavalink.JLavaLinkClient;
import io.lolyay.jlavalink.data.JLavaLinkClientInfo;
import io.lolyay.jlavalink.v4.rest.handlers.ResponseHandler;
import io.lolyay.jlavalink.v4.rest.model.RestErrorResponse;
import io.lolyay.jlavalink.v4.rest.model.RestResponse;
import io.lolyay.jlavalink.v4.rest.packets.Packet;
import io.lolyay.lavaboth.utils.Logger;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class LavaLinkRestClient {
    private final String apiUrl;
    private final String password;
    private final HttpClient httpClient;
    private final Gson gson;
    private final ExecutorService responseHandlerExecutor;

    public LavaLinkRestClient(JLavaLinkClientInfo info) {
        String wsUrl = info.connection().uri().toString() + ":" + info.connection().port();
        // Correctly derive HTTP URL from WebSocket URL
        String httpUrl = wsUrl.startsWith("wss://")
                ? wsUrl.replaceFirst("wss://", "https://")
                : wsUrl.replaceFirst("ws://", "http://");

        this.apiUrl = httpUrl.endsWith("/") ? httpUrl : httpUrl + "/";
        this.password = info.connection().password();
        this.httpClient = HttpClient.newBuilder()
                .executor(Executors.newVirtualThreadPerTaskExecutor())
                .build();
        this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        this.responseHandlerExecutor = Executors.newCachedThreadPool();
    }

    public <T extends RestResponse> void execute(Packet<T> packet, ResponseHandler<T> handler) {
        try {
            HttpRequest request = buildHttpRequest(packet);

            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .whenCompleteAsync((response, error) -> {
                        if (error != null) {
                            handler.onError(error);
                            return;
                        }
                        try {
                            // Check for HTTP error status codes first
                            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                                RestErrorResponse errorResponse = gson.fromJson(response.body(), RestErrorResponse.class);
                                handler.onError(new RuntimeException("Lavalink returned an error: " + errorResponse.message()));
                                return;
                            }
                            // Delegate parsing to the packet-specific parser
                            T parsedResponse = packet.getResponseParser().parse(response, this.gson);
                            handler.onSuccess(parsedResponse);
                        } catch (Exception e) {
                            handler.onError(e);
                        }
                    }, responseHandlerExecutor);

        } catch (Exception e) {
            handler.onError(e);
        }
    }

    private <T extends RestResponse> HttpRequest buildHttpRequest(Packet<T> packet) {
        // This method remains the same and is perfectly generic
        String queryParams = packet.getQueryParams().entrySet().stream()
                .map(entry -> URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        String fullUrl = apiUrl + packet.getPath() + (queryParams.isEmpty() ? "" : "?" + queryParams);

        HttpRequest.BodyPublisher bodyPublisher = packet.getBody()
                .map(body -> packet.toJson())
                .map(HttpRequest.BodyPublishers::ofString)
                .orElse(HttpRequest.BodyPublishers.noBody());

        return HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("Authorization", password)
                .header("Content-Type", "application/json")
                .header("User-Agent", "JLavaLink") // It's good practice to set a User-Agent
                .method(packet.getMethod().name(), bodyPublisher)
                .build();
    }

    public void shutdown() {
        responseHandlerExecutor.shutdown();
    }
}
