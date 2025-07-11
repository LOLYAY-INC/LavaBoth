package io.lolyay.jlavalink.v4.rest.packets;

import io.lolyay.jlavalink.v4.rest.ResponseParser;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestInfoResult;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestStatsResult;
import io.lolyay.jlavalink.v4.rest.parsers.GetStatsParser;
import io.lolyay.jlavalink.v4.rest.parsers.InfoParser;

import java.util.Map;

public class GetStatsPacket implements Packet<RestStatsResult> {

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.GET;
    }

    @Override
    public String getPath() {
        return "v4/stats";
    }

    @Override
    public Map<String, String> getQueryParams() {
        return Map.of();
    }

    @Override
    public ResponseParser<RestStatsResult> getResponseParser() {
        return new GetStatsParser();
    }
}
