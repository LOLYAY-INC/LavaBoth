package io.lolyay.jlavalink.v4.ws.packet.packets.S2C.event;

import com.google.gson.annotations.Expose;
import io.lolyay.jlavalink.v4.ws.packet.handlers.NoPacket;
import io.lolyay.lavaboth.LavaBoth;
import io.lolyay.lavaboth.backends.common.MusicTrackEndReason;
import io.lolyay.lavaboth.backends.lavalinkclient.player.LavaLinkPlayer;
import io.lolyay.lavaboth.tracks.MusicAudioTrack;

@NoPacket
public class TrackStuckEvent extends S2CBaseEventPacket {
    @Expose
    public long thresholdMs;


    @Override
    public void recivePacket() {
        LavaLinkPlayer player = getClient().getPlayerManager().getPlayerFactory().getOrCreatePlayer(Long.parseLong(guildId));
        LavaBoth.eventBus.post(new io.lolyay.lavaboth.events.track.TrackStuckEvent(
                MusicAudioTrack.fromTrack(track,track.getUserData()
                ), player, getClient().getPlayerManager(), thresholdMs));
    }
}