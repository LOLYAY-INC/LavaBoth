package io.lolyay.jlavalink.v4.rest.packets;

import io.lolyay.jlavalink.v4.rest.ResponseParser;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestAllPlayersResult;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestGetPlayerResult;
import io.lolyay.jlavalink.v4.rest.parsers.GetAllPlayersParser;
import io.lolyay.jlavalink.v4.rest.parsers.GetGuildPlayerParser;

import java.util.Map;

public class GetGuildPlayerPacket implements Packet<RestGetPlayerResult> {
    private final String sessionId;
    private final String guildId;

    public GetGuildPlayerPacket(String sessionId, String guildId) {
        this.sessionId = sessionId;
        this.guildId = guildId;
    }


    @Override
    public HttpMethod getMethod() {
        return HttpMethod.GET;
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
    public ResponseParser<RestGetPlayerResult> getResponseParser() {
        return new GetGuildPlayerParser();
    }
}
