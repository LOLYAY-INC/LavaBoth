package io.lolyay.jlavalink;

import io.lolyay.jlavalink.v4.datatypes.ClientPlayer;
import io.lolyay.jlavalink.v4.datatypes.ClientTrack;
import io.lolyay.jlavalink.v4.rest.handlers.ResponseHandler;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestEmptyResult;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestGetPlayerResult;
import io.lolyay.jlavalink.v4.rest.packets.DestroyPlayerPacket;
import io.lolyay.jlavalink.v4.rest.packets.UpdatePlayerPacket;
import net.dv8tion.jda.internal.requests.CompletedRestAction;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ClientPlayerFactory {
    private final JLavaLinkClient client;
    private final Map<String , ClientPlayer> players = new HashMap<>();

    public ClientPlayerFactory(JLavaLinkClient client) {
        this.client = client;
    }

    public ClientPlayer getOrCreatePlayer(String guildId) {
        if(!players.containsKey(guildId)) {
            ClientPlayer player = iCreatePlayer(guildId);
            players.put(guildId, player);
            return player;
        } else {
            return players.get(guildId);
        }
    }

    public CompletableFuture<ClientPlayer> deletePlayer(String guildId) {
        CompletableFuture<ClientPlayer> future = new CompletableFuture<>();
        client.getRestSender().execute(
                new DestroyPlayerPacket(client.getSessionId(),guildId),
                new ResponseHandler<>() {
                    @Override
                    public void onSuccess(RestEmptyResult response) {
                        future.complete(players.remove(guildId));
                    }

                    @Override
                    public void onError(Throwable error) {
                        future.completeExceptionally(error);
                    }
                }
        );
        return future;
    }







    // PRIVATE HELPERS

    private ClientPlayer iCreatePlayer(String guildId) {
        UpdatePlayerPacket.Request request = new UpdatePlayerPacket.Request();
        ClientPlayer player = new ClientPlayer();
        player.guildId = guildId;
        player.client = client;
        return player;
    }


}
