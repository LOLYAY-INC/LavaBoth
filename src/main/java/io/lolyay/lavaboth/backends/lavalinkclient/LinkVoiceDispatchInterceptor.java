package io.lolyay.lavaboth.backends.lavalinkclient;

import io.lolyay.jlavalink.JLavaLinkClient;
import io.lolyay.jlavalink.v4.datatypes.ClientPlayer;
import net.dv8tion.jda.api.hooks.VoiceDispatchInterceptor;
import org.jetbrains.annotations.NotNull;

public class LinkVoiceDispatchInterceptor implements VoiceDispatchInterceptor {
    private final JLavaLinkClient lavaLinkClient;

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
    }

    @Override
    public boolean onVoiceStateUpdate(@NotNull VoiceDispatchInterceptor.VoiceStateUpdate voiceStateUpdate) {
        return lavaLinkClient.getClientPlayerFactory().getOrCreatePlayer(voiceStateUpdate.getGuildId()).playerState.connected;
    }
}
