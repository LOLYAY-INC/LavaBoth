package io.lolyay.jlavalink.v4.rest.packets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import dev.arbjerg.lavalink.protocol.v4.Message;
import io.lolyay.jlavalink.v4.datatypes.ClientPlayer;
import io.lolyay.jlavalink.v4.rest.ResponseParser;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestGetPlayerResult;
import io.lolyay.jlavalink.v4.rest.parsers.GetGuildPlayerParser;
import io.lolyay.lavaboth.tracks.RequestorData;
import io.lolyay.lavaboth.utils.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.Map;
import java.util.Optional;

public class UpdatePlayerPacket implements Packet<RestGetPlayerResult> {
    private final boolean replace;
    private final String guildId;
    private final String sessionId;
    private final Request request;

    public UpdatePlayerPacket(boolean replace, String guildId, String sessionId,
                              Request request) {
        this.replace = replace;
        this.guildId = guildId;
        this.sessionId = sessionId;
        this.request = request;
    }

    public Optional<Object> getBody() {
        return Optional.of(request);
    }

    @Override
    public String toJson() {
        return request.toJson();
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.PATCH;
    }

    @Override
    public String getPath() {
        return "v4/sessions/%s/players/%s".formatted(sessionId,guildId);
    }

    @Override
    public Map<String, String> getQueryParams() {
        return Map.of("noReplace", String.valueOf(!replace));
    }

    @Override
    public ResponseParser<RestGetPlayerResult> getResponseParser() {
        return new GetGuildPlayerParser();
    }

    public static class Request {
        @Expose
        public UpdatePlayerTrack track;

        @Expose
        @SerializedName("position")
        @JsonAdapter(ZeroExclusionAdapter.class)
        public int position;

        @Expose
        @SerializedName("volume")
        @JsonAdapter(ZeroExclusionAdapter.class)
        public int volume;

        @Expose
        @SerializedName("endTime")
        @JsonAdapter(ZeroExclusionAdapter.class)
        public long endTime;

        @Expose
        public boolean paused;

        @Expose
        public ClientPlayer.Filters filters;

        @Expose
        public ClientPlayer.VoiceState voice;

        private static class ZeroExclusionAdapter extends TypeAdapter<Number> {
            @Override
            public void write(JsonWriter out, Number value) throws IOException {
                if (value == null || value.intValue() == 0) {
                    out.nullValue();
                } else {
                    out.value(value);
                }
            }

            @Override
            public Number read(JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return 0;
                }
                return in.nextLong();
            }
        }

        public String toJson() {
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .registerTypeAdapter(int.class, new ZeroExclusionAdapter())
                    .registerTypeAdapter(long.class, new ZeroExclusionAdapter())
                    .create();
            return gson.toJson(this);
        }
    }


    public static class UpdatePlayerTrack{
        @Expose
        @Nullable
        public String encoded;
        @Expose
        @Nullable
        public RequestorData userData;
        @Expose
        @Nullable
        public String identifier;
    }
}
