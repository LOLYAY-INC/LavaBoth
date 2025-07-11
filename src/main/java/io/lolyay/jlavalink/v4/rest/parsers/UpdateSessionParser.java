// Full Path: src/io/lolyay/jlavalink/v4/rest/handlers/parsers/DecodeTrackParser.java
package io.lolyay.jlavalink.v4.rest.parsers;

import com.google.gson.Gson;
import io.lolyay.jlavalink.v4.rest.ResponseParser;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestDecodeResult;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestUpdateSessionResult;

import java.net.http.HttpResponse;

public class UpdateSessionParser implements ResponseParser<RestUpdateSessionResult> {
    @Override
    public RestUpdateSessionResult parse(HttpResponse<String> response, Gson gson) {
        return gson.fromJson(response.body(), RestUpdateSessionResult.class);
    }
}