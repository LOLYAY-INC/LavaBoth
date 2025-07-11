package io.lolyay.jlavalink.v4.rest.packets;

import io.lolyay.jlavalink.v4.rest.ResponseParser;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestInfoResult;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestStringResponse;
import io.lolyay.jlavalink.v4.rest.parsers.InfoParser;
import io.lolyay.jlavalink.v4.rest.parsers.StringParser;

import java.util.Map;

public class GetVersionPacket implements Packet<RestStringResponse> {

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.GET;
    }

    @Override
    public String getPath() {
        return "version";
    }

    @Override
    public Map<String, String> getQueryParams() {
        return Map.of();
    }

    @Override
    public ResponseParser<RestStringResponse> getResponseParser() {
        return new StringParser();
    }
}
