// Full Path: src/io/lolyay/jlavalink/v4/rest/handlers/parsers/DecodeTrackParser.java
package io.lolyay.jlavalink.v4.rest.parsers;

import com.google.gson.Gson;
import io.lolyay.jlavalink.v4.rest.ResponseParser;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestDecodeResult;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestEmptyResult;

import java.net.http.HttpResponse;

public class EmptyParser implements ResponseParser<RestEmptyResult> {
    @Override
    public RestEmptyResult parse(HttpResponse<String> response, Gson gson) {
        return new RestEmptyResult();
    }
}