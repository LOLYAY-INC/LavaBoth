package io.lolyay.lavaboth.events.track;

import io.lolyay.lavaboth.backends.common.AbstractPlayer;
import io.lolyay.lavaboth.backends.common.AbstractPlayerManager;
import io.lolyay.lavaboth.backends.lavaplayer.player.LavaPlayerPlayer;
import io.lolyay.lavaboth.backends.lavaplayer.player.LavaPlayerPlayerManager;
import io.lolyay.lavaboth.tracks.MusicAudioTrack;

/**
 * Fired when a track is stuck.
 * Exclusive to {@link LavaPlayerPlayer}
 *
 */
public class TrackStuckEvent extends TrackEvent {
    private final long stuckTimeMs;
    public TrackStuckEvent(MusicAudioTrack track, AbstractPlayer player, AbstractPlayerManager playerManager, long stuckTimeMs) {
        super(track, player, playerManager);
        this.stuckTimeMs = stuckTimeMs;
    }

    public long getStuckTimeMs() {
        return stuckTimeMs;
    }
}
