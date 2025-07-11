package io.lolyay.lavaboth.backends.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import io.lolyay.lavaboth.LavaBoth;
import io.lolyay.lavaboth.backends.common.MusicTrackEndReason;
import io.lolyay.lavaboth.exceptions.PlayingException;
import io.lolyay.lavaboth.backends.lavaplayer.player.LavaPlayerPlayer;
import io.lolyay.lavaboth.backends.lavaplayer.player.LavaPlayerPlayerManager;
import io.lolyay.lavaboth.events.track.*;

public class LavaPlayerEventTranslator extends AudioEventAdapter {
    private final LavaPlayerPlayerManager playerManager;
    private final LavaPlayerPlayer lavaplayer;

    public LavaPlayerEventTranslator(LavaPlayerPlayerManager playerManager, LavaPlayerPlayer player) {
        this.playerManager = playerManager;
        this.lavaplayer = player;
    }

    @Override
    public void onPlayerPause(AudioPlayer player) {
        LavaBoth.eventBus.post(new TrackPausedEvent(lavaplayer.getCurrentTrack(), lavaplayer, playerManager));
    }

    @Override
    public void onPlayerResume(AudioPlayer player) {
        LavaBoth.eventBus.post(new TrackUnPausedEvent(lavaplayer.getCurrentTrack(), lavaplayer, playerManager));
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        LavaBoth.eventBus.post(new TrackStartedEvent(lavaplayer.getCurrentTrack(), lavaplayer, playerManager));
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        LavaBoth.eventBus.post(new TrackEndEvent(lavaplayer.getCurrentTrack(), lavaplayer, playerManager, MusicTrackEndReason.fromAudioTrackEndReason(endReason)));

    }

    @Override
    public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
        LavaBoth.eventBus.post(new TrackErrorEvent(lavaplayer.getCurrentTrack(), lavaplayer, playerManager,(PlayingException) exception));
    }

    @Override
    public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
        LavaBoth.eventBus.post(new TrackStuckEvent(lavaplayer.getCurrentTrack(), lavaplayer, playerManager, thresholdMs));
    }
}
