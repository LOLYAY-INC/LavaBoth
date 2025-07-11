package io.lolyay.jlavalink.v4.rest.handlers.resthandlers;

import io.lolyay.jlavalink.v4.rest.model.RestResponse;

public class RestStringResponse implements RestResponse {
    public String value;

    @Override
    public String toString() {
        return value;
    }
}
