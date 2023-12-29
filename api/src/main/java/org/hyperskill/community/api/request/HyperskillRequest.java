package org.hyperskill.community.api.request;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import org.hyperskill.community.api.response.HyperskillResponse;

/**
 * @param <T> the final response this request yields.
 */
public abstract class HyperskillRequest<T extends HyperskillResponse> {
    public enum HttpMethod {
        GET, POST, PUT, PATCH, DELETE
    }

    private static final ObjectMapper mapper = new ObjectMapper();

    private final HttpMethod method;
    private final HttpRequest.BodyPublisher bodyPublisher;
    private final Class<T> responseType;

    private String uri = "https://hyperskill.org/api";

    /**
     * @param method        Http method to use
     * @param path          API path
     * @param responseType  parsed response class
     * @param bodyPublisher request body
     */
    protected HyperskillRequest(HttpMethod method, String path, Class<T> responseType, @Nullable HttpRequest.BodyPublisher bodyPublisher) {
        this(method, path, responseType, bodyPublisher, null);
    }

    /**
     * @param method        Http method to use
     * @param path          API path
     * @param responseType  parsed response class
     * @param parameters    query parameters of request
     */
    protected HyperskillRequest(HttpMethod method, String path, Class<T> responseType, @Nullable Map<String, Object> parameters) {
        this(method, path, responseType, null, parameters);
    }

    /**
     * @param method        Http method to use
     * @param path          API path
     * @param responseType  parsed response class
     * @param bodyPublisher request body
     * @param parameters    query parameters of request
     */
    protected HyperskillRequest(HttpMethod method, String path, Class<T> responseType, @Nullable HttpRequest.BodyPublisher bodyPublisher, @Nullable Map<String, Object> parameters) {
        this.method = method;
        this.responseType = responseType;
        this.bodyPublisher = Objects.requireNonNullElse(bodyPublisher, HttpRequest.BodyPublishers.noBody());
        this.uri += path;

        if (parameters != null && !parameters.isEmpty())
            uri += "?" + parameters.entrySet().stream()
                .map(parameter -> URLEncoder.encode(parameter.getKey(), StandardCharsets.UTF_8) + "=" + URLEncoder.encode(parameter.getValue().toString(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public Class<T> getResponseType() {
        return responseType;
    }

    /**
     * Makes a {@link HttpRequest} to send using {@link java.net.http.HttpClient}.
     */
    public HttpRequest formRequest() {
        return HttpRequest.newBuilder(URI.create(getUri()))
            .headers(makeHeaderArray(headers()))
            .method(getMethod().name(), bodyPublisher)
            .build();
    }

    /**
     * Parses json string to the corresponding response class.
     *
     * @param json The string json
     * @return     Parsed response class
     */
    public T parseResponse(String json) throws JsonProcessingException {
        return mapper.readValue(json, responseType);
    }

    protected Map<String, Object> headers() {
        return Map.of();
    }

    protected static String[] makeHeaderArray(Map<String, Object> headers) {
        return headers.keySet().stream()
            .flatMap(key -> Stream.of(key, headers.get(key).toString()))
            .toList()
            .toArray(new String[0]);
    }
}
