package io.lolyay.jlavalink.v4.datatypes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.lolyay.jlavalink.ClientPlayerFactory;
import io.lolyay.jlavalink.JLavaLinkClient;
import io.lolyay.jlavalink.v4.rest.handlers.ResponseHandler;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestDecodeResult;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestGetPlayerResult;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestLoadResult;
import io.lolyay.jlavalink.v4.rest.packets.DecodeTracksPacket;
import io.lolyay.jlavalink.v4.rest.packets.LoadTracksPacket;
import io.lolyay.jlavalink.v4.rest.packets.UpdatePlayerPacket;
import io.lolyay.lavaboth.tracks.RequestorData;
import net.dv8tion.jda.internal.requests.CompletedRestAction;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ClientPlayer {
    public JLavaLinkClient client;



    public CompletableFuture<ClientPlayer> playTrack(@NotNull ClientTrack track, RequestorData requestorData) {
        assert client != null;
        UpdatePlayerPacket.Request request = new UpdatePlayerPacket.Request();
        request.track = new UpdatePlayerPacket.UpdatePlayerTrack();
        request.track.encoded = track.getEncoded();
        request.track.userData = requestorData;
        request.endTime = track.getTrackInfo().getLength();
        CompletableFuture<ClientPlayer> future = new CompletableFuture<>();
        client.getRestSender().execute(
                new UpdatePlayerPacket(true, guildId, client.getSessionId(), request),
                new ResponseHandler<>() {
                    @Override
                    public void onSuccess(RestGetPlayerResult response) {
                        currentTrack = track;
                        future.complete(response);
                    }
                    @Override
                    public void onError(Throwable error) {
                        future.completeExceptionally(error);
                    }
                }
        );
        return future;
    }

    public CompletableFuture<ClientPlayer> setVolume(int volume) {
        assert client != null;
        UpdatePlayerPacket.Request request = new UpdatePlayerPacket.Request();
        request.volume = volume;
        CompletableFuture<ClientPlayer> future = new CompletableFuture<>();
        sendPlayerUpdate(request, future);
        return future;
    }

    public CompletableFuture<ClientPlayer> setPaused(boolean paused) {
        assert client != null;
        UpdatePlayerPacket.Request request = new UpdatePlayerPacket.Request();
        request.paused = paused;
        CompletableFuture<ClientPlayer> future = new CompletableFuture<>();
        sendPlayerUpdate(request, future);
        return future;
    }

    public CompletableFuture<ClientPlayer> stopTrack() {
        assert client != null;
        UpdatePlayerPacket.Request request = new UpdatePlayerPacket.Request();
        request.track = new UpdatePlayerPacket.UpdatePlayerTrack();
        request.track.encoded = null;
        CompletableFuture<ClientPlayer> future = new CompletableFuture<>();
        client.getRestSender().execute(
                new UpdatePlayerPacket(true, guildId, client.getSessionId(), request),
                new ResponseHandler<>() {
                    @Override
                    public void onSuccess(RestGetPlayerResult response) {
                        future.complete(response);
                    }
                    @Override
                    public void onError(Throwable error) {
                        future.completeExceptionally(error);
                    }
                }
        );
        return future;
    }


    public CompletableFuture<RestLoadResult<?>> loadTracks(String query) {
        return client.loadTracks(query);
    }

    public CompletableFuture<ClientTrack> decodeTrack(@NotNull String encoded) {
        return client.decodeTrack(encoded);
    }




    public CompletableFuture<ClientPlayer> setVoice(VoiceState voiceState) {
        assert client != null;
        UpdatePlayerPacket.Request request = new UpdatePlayerPacket.Request();
        request.voice = voiceState;
        CompletableFuture<ClientPlayer> future = new CompletableFuture<>();
        client.getRestSender().execute(
                new UpdatePlayerPacket(true, guildId, client.getSessionId(), request),
                new ResponseHandler<>() {
                    @Override
                    public void onSuccess(RestGetPlayerResult response) {
                        future.complete(response);
                    }
                    @Override
                    public void onError(Throwable error) {
                        future.completeExceptionally(error);
                    }
                }
        );
        return future;
    }






    private void sendPlayerUpdate(UpdatePlayerPacket.Request request, CompletableFuture<ClientPlayer> future) {
        client.getRestSender().execute(
                new UpdatePlayerPacket(false, guildId, client.getSessionId(), request),
                new ResponseHandler<>() {
                    @Override
                    public void onSuccess(RestGetPlayerResult response) {
                        future.complete(response);
                    }
                    @Override
                    public void onError(Throwable error) {
                        future.completeExceptionally(error);
                    }
                }
        );
    }


    // Json Shit
    @Expose
    public String guildId;

    @Expose
    @Nullable
    @SerializedName("track")
    public ClientTrack currentTrack;

    @Expose
    public int volume;

    @Expose
    public boolean paused;

    @Expose
    @SerializedName("state")
    public PlayerState playerState;

    @Expose
    @SerializedName("voice")
    public VoiceState voiceState;

    @Expose
    public Filters filters; //TODO


    public static class PlayerState {
        @Expose
        public long time;
        @Expose
        public int position;
        @Expose
        public boolean connected;
        @Expose
        public int ping;
    }

    public static class VoiceState {
        @Expose
        public String token;
        @Expose
        public String sessionId;
        @Expose
        public String endpoint;
    }

    public static class Filters {
        //TODO: THIS SHIT IS FUCKED https://lavalink.dev/api/rest.html#filters
    }
}
