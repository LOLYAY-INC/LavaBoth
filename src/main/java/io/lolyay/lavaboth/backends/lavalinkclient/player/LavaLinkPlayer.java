package io.lolyay.lavaboth.backends.lavalinkclient.player;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import io.lolyay.jlavalink.ClientPlayerFactory;
import io.lolyay.jlavalink.JLavaLinkClient;
import io.lolyay.jlavalink.v4.datatypes.ClientPlayer;
import io.lolyay.jlavalink.v4.datatypes.ClientTrack;
import io.lolyay.lavaboth.backends.common.AbstractPlayer;
import io.lolyay.lavaboth.backends.common.TrackDecodingException;
import io.lolyay.lavaboth.backends.lavalinkclient.LavaLinkTrackCoder;
import io.lolyay.lavaboth.backends.lavalinkclient.LinkVoiceDispatchInterceptor;
import io.lolyay.lavaboth.tracks.MusicAudioTrack;
import io.lolyay.lavaboth.tracks.RequestorData;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;

import java.util.concurrent.CompletableFuture;

public class LavaLinkPlayer extends AbstractPlayer {
    private final ClientPlayer clientPlayer;
    private final LavaLinkPlayerManager audioPlayerManager;
    private final JLavaLinkClient lavaLinkClient;

    public LavaLinkPlayer(ClientPlayer audioPlayer, LavaLinkPlayerManager audioPlayerManager, JLavaLinkClient lavaLinkClient) {
        super(Long.valueOf(audioPlayer.guildId));
        this.clientPlayer = audioPlayer;
        this.audioPlayerManager = audioPlayerManager;
        this.lavaLinkClient = lavaLinkClient;
    }

    @Override
    public void play(MusicAudioTrack track) {
        try {
            ClientTrack clientTrack = new LavaLinkTrackCoder(lavaLinkClient).decode(track.encodedTrack());
            clientPlayer.playTrack(clientTrack, track.userData()).join();
        } catch (TrackDecodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
        clientPlayer.stopTrack().join();
    }

    @Override
    public void pause() {
        clientPlayer.setPaused(true).join();
    }

    @Override
    public void resume() {
        clientPlayer.setPaused(false).join();
    }

    @Override
    public boolean isPaused() {
        return clientPlayer.paused;
    }

    @Override
    public int getVolume() {
        return clientPlayer.volume;
    }

    @Override
    public void setVolume(int volume) {
        clientPlayer.setVolume(volume).join();
    }

    @Override
    public MusicAudioTrack getCurrentTrack() {
        return MusicAudioTrack.fromTrack(clientPlayer.currentTrack,clientPlayer.currentTrack.getUserData());
    }

    @Override
    public void connect(AudioChannel channel) {
        if(channel.getGuild().getSelfMember().getVoiceState() == null || !channel.getGuild().getSelfMember().getVoiceState().inAudioChannel()) {
            CompletableFuture<LavaLinkPlayer> future = new CompletableFuture<>();
            LinkVoiceDispatchInterceptor.connectWaitFuture = future;
            channel.getJDA().getDirectAudioController().connect(channel);
            future.join();
            clientPlayer.shouldBePlaying = true;
        }

    }

    @Override
    public void disconnect(Guild guild) {
        clientPlayer.shouldBePlaying = false;
        guild.getJDA().getDirectAudioController().disconnect(guild);
    }

    public ClientPlayer getAudioPlayer() {
        return clientPlayer;
    }
}
