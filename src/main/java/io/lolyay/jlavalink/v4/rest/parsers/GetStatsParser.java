// Full Path: src/io/lolyay/jlavalink/v4/rest/handlers/parsers/DecodeTrackParser.java
package io.lolyay.jlavalink.v4.rest.parsers;

import com.google.gson.Gson;
import io.lolyay.jlavalink.v4.rest.ResponseParser;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestGetPlayerResult;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestStatsResult;

import java.net.http.HttpResponse;

public class GetStatsParser implements ResponseParser<RestStatsResult> {
    @Override
    public RestStatsResult parse(HttpResponse<String> response, Gson gson) {
        return gson.fromJson(response.body(), RestStatsResult.class);
    }
}