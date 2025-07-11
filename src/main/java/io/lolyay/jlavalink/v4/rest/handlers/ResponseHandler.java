package io.lolyay.jlavalink.v4.rest.handlers;


import io.lolyay.jlavalink.v4.rest.model.RestResponse;

/**
 * A dedicated handler for processing the result of a Packet execution.
 * This is the "Response Acceptor".
 *
 * @param <T> The type of RestResponse this handler expects on success.
 */
public interface ResponseHandler<T extends RestResponse> {

    /**
     * Called when the request is successful and the response has been parsed.
     *
     * @param response The strongly-typed, parsed response object.
     */
    void onSuccess(T response);

    /**
     * Called when any error occurs during the request lifecycle.
     * This includes network errors, HTTP error codes (4xx, 5xx), and parsing failures.
     *
     * @param error The exception that occurred.
     */
    void onError(Throwable error);
}