// Full Path: src/io/lolyay/jlavalink/v4/rest/handlers/ResponseParser.java
package io.lolyay.jlavalink.v4.rest;

import com.google.gson.Gson;
import io.lolyay.jlavalink.v4.rest.model.RestResponse;
import java.net.http.HttpResponse;

/**
 * A functional interface for parsing an HTTP response into a specific RestResponse type.
 * @param <T> The target RestResponse type.
 */
@FunctionalInterface
public interface ResponseParser<T extends RestResponse> {
    /**
     * Parses the HTTP response.
     * @param response The raw HTTP response from the server.
     * @param gson A Gson instance to use for deserialization.
     * @return The parsed response object.
     */
    T parse(HttpResponse<String> response, Gson gson);
}
