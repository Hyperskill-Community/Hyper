package org.hyperskill.community.api;

import java.io.UncheckedIOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.hyperskill.community.api.request.HyperskillRequest;
import org.hyperskill.community.api.response.HyperskillResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HyperskillAPI {
    private static final Logger logger = LoggerFactory.getLogger(HyperskillAPI.class);

    private final HttpClient httpClient = HttpClient.newHttpClient();

    /**
     * Sends a request to Hyperskill public API.
     *
     * @param <T>     Response type from hyperskill
     * @param request Request to send
     * @return        CompletableFuture which yields response, throws {@link UncheckedIOException} if json parsing fails
     */
    public <T extends HyperskillResponse> CompletableFuture<T> sendRequest(HyperskillRequest<T> request) {
        return httpClient.sendAsync(request.formRequest(), HttpResponse.BodyHandlers.ofString())
            .thenApply(response -> {
                try {
                    return request.parseResponse(response.body());
                } catch (JsonProcessingException e) {
                    logger.debug("Json parsing failed, json: {}, class: {}", response.body(), request.getResponseType());
                    throw new UncheckedIOException(e);
                }
            });
    }
}
