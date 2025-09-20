package io.lolyay.lavaboth.backends.lavaplayer.player;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import io.lolyay.lavaboth.backends.common.AbstractPlayer;
import io.lolyay.lavaboth.backends.common.TrackDecodingException;
import io.lolyay.lavaboth.backends.lavaplayer.AudioSendHandler;
import io.lolyay.lavaboth.backends.lavaplayer.LavaPlayerTrackCoder;
import io.lolyay.lavaboth.tracks.MusicAudioTrack;
import io.lolyay.lavaboth.tracks.RequestorData;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;

public class LavaPlayerPlayer extends AbstractPlayer {
    private final AudioPlayer audioPlayer;
    private final LavaPlayerPlayerManager audioPlayerManager;

    public LavaPlayerPlayer(AudioPlayer audioPlayer, LavaPlayerPlayerManager audioPlayerManager, Long guildId) {
        super(guildId);
        this.audioPlayer = audioPlayer;
        this.audioPlayerManager = audioPlayerManager;
    }

    @Override
    public void play(MusicAudioTrack track) {
        LavaPlayerTrackCoder coder = new LavaPlayerTrackCoder(audioPlayerManager.getAudioPlayerManager());
        try {
            AudioTrack audioTrack = coder.decode(track.encodedTrack());
            audioTrack.setUserData(track.userData());
            audioPlayer.playTrack(audioTrack);
        } catch (TrackDecodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
        audioPlayer.stopTrack();
    }

    @Override
    public void pause() {
        audioPlayer.setPaused(true);

    }

    @Override
    public void resume() {
        audioPlayer.setPaused(false);
    }

    @Override
    public boolean isPaused() {
        return audioPlayer.isPaused();
    }

    @Override
    public int getVolume() {
        return audioPlayer.getVolume();
    }

    @Override
    public void setVolume(int volume) {
        audioPlayer.setVolume(volume);
    }

    @Override
    public MusicAudioTrack getCurrentTrack() {
        if(audioPlayer.getPlayingTrack() == null) return null;
        return MusicAudioTrack.fromTrack(audioPlayerManager,audioPlayer.getPlayingTrack(),(RequestorData) audioPlayer.getPlayingTrack().getUserData());
    }

    @Override
    public void connect(AudioChannel channel) {
        if(!channel.getGuild().getAudioManager().isConnected()) {
            channel.getGuild().getAudioManager().setSendingHandler(new AudioSendHandler(audioPlayer));
            channel.getGuild().getAudioManager().openAudioConnection(channel);
        }
    }

    @Override
    public void disconnect(Guild guild) {
        guild.getAudioManager().closeAudioConnection();
    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }
}
