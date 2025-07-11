package io.lolyay.jlavalink.v4.rest.packets;

import io.lolyay.jlavalink.v4.rest.ResponseParser;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestEmptyResult;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestGetPlayerResult;
import io.lolyay.jlavalink.v4.rest.parsers.EmptyParser;
import io.lolyay.jlavalink.v4.rest.parsers.GetGuildPlayerParser;

import java.util.Map;

public class DestroyPlayerPacket implements Packet<RestEmptyResult> {
    private final String sessionId;
    private final String guildId;

    public DestroyPlayerPacket(String sessionId, String guildId) {
        this.sessionId = sessionId;
        this.guildId = guildId;
    }


    @Override
    public HttpMethod getMethod() {
        return HttpMethod.DELETE;
    }

    @Override
    public String getPath() {
        return "v4/sessions/%s/players/%s".formatted(sessionId,guildId);
    }

    @Override
    public Map<String, String> getQueryParams() {
        return Map.of();
    }

    @Override
    public ResponseParser<RestEmptyResult> getResponseParser() {
        return new EmptyParser();
    }
}
