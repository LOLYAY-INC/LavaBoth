package io.lolyay.jlavalink.v4.rest.packets;

import io.lolyay.jlavalink.v4.rest.ResponseParser;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestEmptyResult;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestInfoResult;
import io.lolyay.jlavalink.v4.rest.parsers.EmptyParser;
import io.lolyay.jlavalink.v4.rest.parsers.InfoParser;

import java.util.Map;

public class GetInfoPacket implements Packet<RestInfoResult> {

    @Override
    public Packet.HttpMethod getMethod() {
        return HttpMethod.GET;
    }

    @Override
    public String getPath() {
        return "v4/info";
    }

    @Override
    public Map<String, String> getQueryParams() {
        return Map.of();
    }

    @Override
    public ResponseParser<RestInfoResult> getResponseParser() {
        return new InfoParser();
    }
}
