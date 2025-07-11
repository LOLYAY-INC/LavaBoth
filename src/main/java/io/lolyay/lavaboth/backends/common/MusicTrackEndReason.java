package io.lolyay.lavaboth.backends.common;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import dev.arbjerg.lavalink.protocol.v4.Message;
import io.lolyay.jlavalink.v4.datatypes.ClientTrackEndReason;

public enum MusicTrackEndReason {
    FINISHED(true),
    LOAD_FAILED(true),
    STOPPED(false),
    REPLACED(false),
    CLEANUP(false);

    public final boolean mayStartNext;

    MusicTrackEndReason(boolean mayStartNext) {
        this.mayStartNext = mayStartNext;
    }

    public static MusicTrackEndReason fromAudioTrackEndReason(AudioTrackEndReason reason) {
        return valueOf(reason.name());
    }

    public static MusicTrackEndReason fromClientTrackEndReason(ClientTrackEndReason reason) {
        return valueOf(reason.name());
    }
}
