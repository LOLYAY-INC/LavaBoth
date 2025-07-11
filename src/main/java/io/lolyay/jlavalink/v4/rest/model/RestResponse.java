package io.lolyay.jlavalink.v4.rest.model;

public interface RestResponse {
    default boolean isError() {
        return this instanceof RestErrorResponse;
    }
}