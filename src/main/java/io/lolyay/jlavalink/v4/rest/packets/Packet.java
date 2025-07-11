package io.lolyay.jlavalink.v4.rest.packets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.lolyay.jlavalink.v4.rest.ResponseParser;
import io.lolyay.jlavalink.v4.rest.model.RestResponse;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * Describes a request to be sent to the Lavalink REST API.
 * This is a stateless data object with no logic.
 *
 * @param <T> The type of RestResponse this packet expects on success.
 */
public interface Packet<T extends RestResponse> {

    Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            .create();

    enum HttpMethod { GET, POST, PATCH, DELETE }

    /** The HTTP method for this request. */
    HttpMethod getMethod();

    /** The path for the endpoint (e.g., "v4/loadtracks"). */
    String getPath();

    /** The query parameters for the request. Defaults to empty. */
    default Map<String, String> getQueryParams() {
        return Collections.emptyMap();
    }

    /** The request body object, serialized to JSON. Empty for GET/DELETE. */
    default Optional<Object> getBody() {
        return Optional.empty();
    }

    /** Default JSON serialization for the body. */
    default String toJson() {
        return getBody().map(GSON::toJson).orElse("");
    }

    ResponseParser<T> getResponseParser();

}