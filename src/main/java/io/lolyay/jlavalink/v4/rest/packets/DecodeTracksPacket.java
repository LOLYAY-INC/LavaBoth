package io.lolyay.jlavalink.v4.rest.packets;


import io.lolyay.jlavalink.v4.rest.ResponseParser;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestDecodeResult;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestLoadResult;
import io.lolyay.jlavalink.v4.rest.parsers.DecodeTrackParser;

import java.util.Map;


public class DecodeTracksPacket implements Packet<RestDecodeResult> {

    private final String base64Data;

    public DecodeTracksPacket(String base64Data) {
        this.base64Data = base64Data;
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.GET;
    }

    @Override
    public String getPath() {
        return "v4/decodetrack"; // Make sure this matches your Lavalink version
    }

    @Override
    public Map<String, String> getQueryParams() {
        return Map.of("encodedTrack", this.base64Data);
    }


    @Override
    public ResponseParser<RestDecodeResult> getResponseParser() {
        return new DecodeTrackParser();
    }
}