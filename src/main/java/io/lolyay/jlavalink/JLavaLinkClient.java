package io.lolyay.jlavalink;

import io.lolyay.jlavalink.data.JLavaLinkClientInfo;
import io.lolyay.jlavalink.data.ConnectionInfo;
import io.lolyay.jlavalink.v4.datatypes.ClientStats;
import io.lolyay.jlavalink.v4.datatypes.ClientTrack;
import io.lolyay.jlavalink.v4.rest.LavaLinkRestClient;
import io.lolyay.jlavalink.v4.rest.handlers.ResponseHandler;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestDecodeResult;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestLoadResult;
import io.lolyay.jlavalink.v4.rest.packets.DecodeTracksPacket;
import io.lolyay.jlavalink.v4.rest.packets.LoadTracksPacket;
import io.lolyay.jlavalink.v4.ws.LavaLinkWsClient;
import io.lolyay.jlavalink.v4.ws.packet.handlers.PacketInitializer;
import io.lolyay.lavaboth.backends.lavalinkclient.player.LavaLinkPlayerManager;
import io.lolyay.lavaboth.utils.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class JLavaLinkClient {
    private final JLavaLinkClientInfo clientInfo;

    private final LavaLinkPlayerManager playerManager;
    private final LavaLinkWsClient wsClient;
    private final LavaLinkRestClient restSender;
    private String sessionId;

    private final ClientPlayerFactory clientPlayerFactory;
    private ClientStats nodeStats;

    public LavaLinkRestClient getRestSender() {
        return restSender;
    }

    public LavaLinkWsClient getWsClient() {
        return wsClient;
    }

    public LavaLinkPlayerManager getPlayerManager() {
        return playerManager;
    }

    public JLavaLinkClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setNodeStats(ClientStats nodeStats) {
        this.nodeStats = nodeStats;
    }


    public JLavaLinkClient(JLavaLinkClientInfo connectionInfo, LavaLinkPlayerManager playerManager) {
        this.playerManager = playerManager;
        this.clientInfo = connectionInfo;
        this.wsClient = new LavaLinkWsClient(this);
        this.restSender = new LavaLinkRestClient(connectionInfo);

        this.clientPlayerFactory = new ClientPlayerFactory(this);
    }

    public void init(){
        PacketInitializer.initialize();
        wsClient.init();
    }

    // yes, this is a whole lavalink client for this
    // because the kotlin client is a mess


    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public ClientPlayerFactory getClientPlayerFactory() {
        return clientPlayerFactory;
    }

    public ClientStats getNodeStats() {
        return nodeStats;
    }




    public CompletableFuture<RestLoadResult<?>> loadTracks(String query) {
        CompletableFuture<RestLoadResult<?>> future = new CompletableFuture<>();
        getRestSender().execute(
                new LoadTracksPacket(query),
                new ResponseHandler<>() {
                    @Override
                    public void onSuccess(RestLoadResult<?> response) {
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

    public CompletableFuture<ClientTrack> decodeTrack(@NotNull String encoded) {
        CompletableFuture<ClientTrack> future = new CompletableFuture<>();
        getRestSender().execute(
                new DecodeTracksPacket(encoded),
                new ResponseHandler<>() {
                    @Override
                    public void onSuccess(RestDecodeResult response) {
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

}
