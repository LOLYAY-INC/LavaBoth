package io.lolyay.lavaboth.backends.lavalinkclient;

import io.lolyay.jlavalink.JLavaLinkClient;
import io.lolyay.jlavalink.v4.datatypes.ClientPlayer;
import io.lolyay.lavaboth.backends.lavalinkclient.player.LavaLinkPlayer;
import io.lolyay.lavaboth.utils.Logger;
import net.dv8tion.jda.api.hooks.VoiceDispatchInterceptor;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class LinkVoiceDispatchInterceptor implements VoiceDispatchInterceptor {
    private final JLavaLinkClient lavaLinkClient;
    public static CompletableFuture<LavaLinkPlayer> connectWaitFuture = new CompletableFuture<>();

    public LinkVoiceDispatchInterceptor(JLavaLinkClient lavaLinkClient) {
        this.lavaLinkClient = lavaLinkClient;
    }

    @Override
    public void onVoiceServerUpdate(@NotNull VoiceDispatchInterceptor.VoiceServerUpdate voiceServerUpdate) {
        ClientPlayer.VoiceState voiceState = new ClientPlayer.VoiceState();
        voiceState.endpoint = voiceServerUpdate.getEndpoint();
        voiceState.sessionId = voiceServerUpdate.getSessionId();
        voiceState.token = voiceServerUpdate.getToken();
        lavaLinkClient.getClientPlayerFactory().getOrCreatePlayer(voiceServerUpdate.getGuildId()).setVoice(
                voiceState
        ).join();
        connectWaitFuture.complete(lavaLinkClient.getPlayerManager().getPlayerFactory().getOrCreatePlayer(voiceServerUpdate.getGuildIdLong()));
    }

    @Override
    public boolean onVoiceStateUpdate(@NotNull VoiceDispatchInterceptor.VoiceStateUpdate voiceStateUpdate) {
        return true;
    }
}
