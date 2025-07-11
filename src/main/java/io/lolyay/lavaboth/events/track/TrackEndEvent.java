package io.lolyay.lavaboth.events.track;

import io.lolyay.lavaboth.backends.common.AbstractPlayer;
import io.lolyay.lavaboth.backends.common.AbstractPlayerManager;
import io.lolyay.lavaboth.backends.common.MusicTrackEndReason;
import io.lolyay.lavaboth.tracks.MusicAudioTrack;

public class TrackEndEvent extends TrackEvent {
    private final MusicTrackEndReason endReason;

    public TrackEndEvent(MusicAudioTrack track, AbstractPlayer player, AbstractPlayerManager playerManager, MusicTrackEndReason endReason) {
        super(track, player, playerManager);
        this.endReason = endReason;
    }

    public MusicTrackEndReason getEndReason() {
        return endReason;
    }
}
