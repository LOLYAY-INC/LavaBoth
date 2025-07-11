package io.lolyay.lavaboth.events.track;

import io.lolyay.eventbus.Event;
import io.lolyay.lavaboth.backends.common.AbstractPlayer;
import io.lolyay.lavaboth.backends.common.AbstractPlayerManager;
import io.lolyay.lavaboth.backends.lavaplayer.player.LavaPlayerPlayer;
import io.lolyay.lavaboth.backends.lavaplayer.player.LavaPlayerPlayerManager;
import io.lolyay.lavaboth.tracks.MusicAudioTrack;

public abstract class TrackEvent extends Event {
    private final MusicAudioTrack track;
    private final AbstractPlayer player;
    private final AbstractPlayerManager playerManager;

    public TrackEvent(MusicAudioTrack track, AbstractPlayer player, AbstractPlayerManager playerManager) {
        this.track = track;
        this.player = player;
        this.playerManager = playerManager;
    }


    public MusicAudioTrack getTrack() {
        return track;
    }

    public AbstractPlayer getPlayer() {
        return player;
    }

    public AbstractPlayerManager getPlayerManager() {
        return playerManager;
    }
}
