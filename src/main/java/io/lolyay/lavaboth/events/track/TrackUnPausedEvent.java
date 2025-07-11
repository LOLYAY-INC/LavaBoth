package io.lolyay.lavaboth.events.track;

import io.lolyay.lavaboth.backends.common.AbstractPlayer;
import io.lolyay.lavaboth.backends.common.AbstractPlayerManager;
import io.lolyay.lavaboth.backends.lavaplayer.player.LavaPlayerPlayer;
import io.lolyay.lavaboth.backends.lavaplayer.player.LavaPlayerPlayerManager;
import io.lolyay.lavaboth.tracks.MusicAudioTrack;

public class TrackUnPausedEvent extends TrackEvent {
    public TrackUnPausedEvent(MusicAudioTrack track, AbstractPlayer player, AbstractPlayerManager playerManager) {
        super(track, player, playerManager);
    }
}
