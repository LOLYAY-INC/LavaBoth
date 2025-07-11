package io.lolyay.jlavalink.v4.ws.packet.packets.S2C.event;


import com.google.gson.annotations.Expose;
import io.lolyay.jlavalink.v4.datatypes.ClientPlayer;
import io.lolyay.jlavalink.v4.datatypes.ClientTrackEndReason;
import io.lolyay.jlavalink.v4.ws.packet.handlers.NoPacket;
import io.lolyay.lavaboth.LavaBoth;
import io.lolyay.lavaboth.backends.common.MusicTrackEndReason;
import io.lolyay.lavaboth.backends.lavalinkclient.player.LavaLinkPlayer;
import io.lolyay.lavaboth.tracks.MusicAudioTrack;
import org.jetbrains.annotations.NotNull;

// TrackEndEvent.java
@NoPacket
public class TrackEndEvent extends S2CBaseEventPacket {
    @NotNull
    @Expose
    public ClientTrackEndReason reason;

    @Override
    public void recivePacket() {
        LavaLinkPlayer player = getClient().getPlayerManager().getPlayerFactory().getOrCreatePlayer(Long.parseLong(guildId));
        LavaBoth.eventBus.post(new io.lolyay.lavaboth.events.track.TrackEndEvent(
                MusicAudioTrack.fromTrack(track,track.getUserData()
                ), player, getClient().getPlayerManager(), MusicTrackEndReason.fromClientTrackEndReason(reason)));
    }
}