package io.lolyay.jlavalink.v4.rest.packets;


import io.lolyay.jlavalink.v4.rest.ResponseParser;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestLoadResult;
import io.lolyay.jlavalink.v4.rest.parsers.LoadTracksParser;

import java.util.Map;

/**
 * A packet to request loading tracks from the /loadtracks endpoint.
 * Expects a RestLoadResult in response.
 */
public class LoadTracksPacket implements Packet<RestLoadResult<?>> {

    private final String identifier;

    public LoadTracksPacket(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.GET;
    }

    @Override
    public String getPath() {
        return "v4/loadtracks"; // Make sure this matches your Lavalink version
    }

    @Override
    public Map<String, String> getQueryParams() {
        return Map.of("identifier", this.identifier);
    }

    @Override
    public ResponseParser<RestLoadResult<?>> getResponseParser() {
        return new LoadTracksParser();
    }
}