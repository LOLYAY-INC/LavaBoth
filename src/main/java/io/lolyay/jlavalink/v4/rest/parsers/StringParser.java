package io.lolyay.jlavalink.v4.rest.parsers;

import com.google.gson.Gson;
import io.lolyay.jlavalink.v4.rest.ResponseParser;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestInfoResult;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestStringResponse;

import java.net.http.HttpResponse;

public class StringParser implements ResponseParser<RestStringResponse> {
    @Override
    public RestStringResponse parse(HttpResponse<String> response, Gson gson) {
        RestStringResponse result = new RestStringResponse();
        result.value = response.body();
        return result;
    }
}