package io.lolyay.jlavalink.v4.ws.packet.packets.S2C.event;

import io.lolyay.jlavalink.v4.ws.packet.handlers.NoPacket;
import io.lolyay.lavaboth.LavaBoth;
import io.lolyay.lavaboth.backends.common.MusicTrackEndReason;
import io.lolyay.lavaboth.backends.lavalinkclient.player.LavaLinkPlayer;
import io.lolyay.lavaboth.tracks.MusicAudioTrack;

@NoPacket
public class TrackStartEvent extends S2CBaseEventPacket {

    @Override
    public void recivePacket() {
        LavaLinkPlayer player = getClient().getPlayerManager().getPlayerFactory().getOrCreatePlayer(Long.parseLong(guildId));
        LavaBoth.eventBus.post(new io.lolyay.lavaboth.events.track.TrackStartedEvent(
                MusicAudioTrack.fromTrack(track,track.getUserData()
                ), player, getClient().getPlayerManager()));

    }
}
