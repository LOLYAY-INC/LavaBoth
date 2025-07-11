package io.lolyay.lavaboth.events.track;

import io.lolyay.lavaboth.backends.common.AbstractPlayer;
import io.lolyay.lavaboth.backends.common.AbstractPlayerManager;
import io.lolyay.lavaboth.exceptions.PlayingException;
import io.lolyay.lavaboth.backends.lavaplayer.player.LavaPlayerPlayer;
import io.lolyay.lavaboth.backends.lavaplayer.player.LavaPlayerPlayerManager;
import io.lolyay.lavaboth.tracks.MusicAudioTrack;

public class TrackErrorEvent extends TrackEvent {
    private final PlayingException exception;
    public TrackErrorEvent(MusicAudioTrack track, AbstractPlayer player, AbstractPlayerManager playerManager, PlayingException exception) {
        super(track, player, playerManager);
        this.exception = exception;
    }

    public PlayingException getException() {
        return exception;
    }
}
