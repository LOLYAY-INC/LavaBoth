package io.lolyay.jlavalink.v4.rest.packets;

import com.google.gson.annotations.Expose;
import io.lolyay.jlavalink.v4.rest.ResponseParser;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestEmptyResult;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestUpdateSessionResult;
import io.lolyay.jlavalink.v4.rest.parsers.EmptyParser;
import io.lolyay.jlavalink.v4.rest.parsers.UpdateSessionParser;

import java.util.Map;
import java.util.Optional;

public class UpdateSessionPacket implements Packet<RestUpdateSessionResult> {
    private final String sessionId;
    private final Request request;

    public UpdateSessionPacket(String sessionId, boolean resuming, int timeout) {
        this.sessionId = sessionId;
        this.request = new Request();
        request.resuming = resuming;
        request.timeout = timeout;
    }


    @Override
    public HttpMethod getMethod() {
        return HttpMethod.PATCH;
    }

    @Override
    public String getPath() {
        return "v4/sessions/%s".formatted(sessionId);
    }

    @Override
    public Map<String, String> getQueryParams() {
        return Map.of();
    }

    @Override
    public Optional<Object> getBody() {
        return Optional.of(request);
    }

    @Override
    public ResponseParser<RestUpdateSessionResult> getResponseParser() {
        return new UpdateSessionParser();
    }

    private static class Request {
        @Expose
        private boolean resuming;
        @Expose
        private int timeout;
    }
}
