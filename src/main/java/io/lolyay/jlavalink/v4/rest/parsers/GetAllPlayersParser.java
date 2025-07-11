// Full Path: src/io/lolyay/jlavalink/v4/rest/handlers/parsers/DecodeTrackParser.java
package io.lolyay.jlavalink.v4.rest.parsers;

import com.google.gson.Gson;
import io.lolyay.jlavalink.v4.datatypes.ClientPlayer;
import io.lolyay.jlavalink.v4.rest.ResponseParser;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestAllPlayersResult;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestDecodeResult;

import java.net.http.HttpResponse;

public class GetAllPlayersParser implements ResponseParser<RestAllPlayersResult> {
    @Override
    public RestAllPlayersResult parse(HttpResponse<String> response, Gson gson) {
        RestAllPlayersResult result = new RestAllPlayersResult();
        result.players = gson.fromJson(response.body(), ClientPlayer[].class);
        return result;
    }
}