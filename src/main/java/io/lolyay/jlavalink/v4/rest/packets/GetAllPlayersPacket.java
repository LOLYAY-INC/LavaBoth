package io.lolyay.jlavalink.v4.rest.packets;

import io.lolyay.jlavalink.v4.rest.ResponseParser;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestAllPlayersResult;
import io.lolyay.jlavalink.v4.rest.parsers.GetAllPlayersParser;

import java.util.Map;

public class GetAllPlayersPacket implements Packet<RestAllPlayersResult> {
    private final String sessionId;

    public GetAllPlayersPacket(String sessionId) {
        this.sessionId = sessionId;
    }


    @Override
    public HttpMethod getMethod() {
        return HttpMethod.GET;
    }

    @Override
    public String getPath() {
        return "v4/sessions/%s/players".formatted(sessionId);
    }

    @Override
    public Map<String, String> getQueryParams() {
        return Map.of();
    }

    @Override
    public ResponseParser<RestAllPlayersResult> getResponseParser() {
        return new GetAllPlayersParser();
    }
}
