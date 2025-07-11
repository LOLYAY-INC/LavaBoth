package io.lolyay.jlavalink.v4.rest.parsers;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import io.lolyay.jlavalink.v4.rest.ResponseParser;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestLoadResult;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.loadtracks.*;
import java.net.http.HttpResponse;

public class LoadTracksParser implements ResponseParser<RestLoadResult<?>> {

    private static class LoadTypeHelper {
        @Expose
        public LoadResultType loadType;
    }

    @Override
    public RestLoadResult<?> parse(HttpResponse<String> response, Gson gson) {
        String body = response.body();
        LoadTypeHelper helper = gson.fromJson(body, LoadTypeHelper.class);
        if (helper.loadType == null) {
            throw new IllegalStateException("Response JSON for loadtracks does not contain 'loadType' field: " + body);
        }

        return switch (helper.loadType) {
            case TRACK -> gson.fromJson(body, TrackLoadResult.class);
            case PLAYLIST -> gson.fromJson(body, PlaylistLoadResult.class);
            case SEARCH -> gson.fromJson(body, SearchLoadResult.class);
            case EMPTY -> gson.fromJson(body, EmptyLoadResult.class);
            case ERROR -> gson.fromJson(body, ErrorLoadResult.class);
        };
    }
}