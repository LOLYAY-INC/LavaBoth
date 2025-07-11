package io.lolyay.jlavalink.v4.ws.packet.packets.S2C.event;

import com.google.gson.annotations.Expose;
import io.lolyay.jlavalink.v4.datatypes.ClientException;
import io.lolyay.jlavalink.v4.ws.packet.handlers.NoPacket;
import io.lolyay.lavaboth.LavaBoth;
import io.lolyay.lavaboth.backends.common.MusicTrackEndReason;
import io.lolyay.lavaboth.backends.lavalinkclient.player.LavaLinkPlayer;
import io.lolyay.lavaboth.exceptions.PlayingException;
import io.lolyay.lavaboth.tracks.MusicAudioTrack;
import io.lolyay.lavaboth.utils.Logger;
import org.jetbrains.annotations.NotNull;


@NoPacket

public class TrackExceptionEvent extends S2CBaseEventPacket {
    @NotNull
    @Expose
    public ClientException exception;



    @Override
    public void recivePacket() {
        Logger.err("Error playing track: " + exception.getMessage());
        Logger.err(exception.getCauseStr());
        Logger.err(exception.getCauseStackTrace());
        LavaLinkPlayer player = getClient().getPlayerManager().getPlayerFactory().getOrCreatePlayer(Long.parseLong(guildId));
        LavaBoth.eventBus.post(new io.lolyay.lavaboth.events.track.TrackErrorEvent(
                MusicAudioTrack.fromTrack(track,track.getUserData()
                ), player, getClient().getPlayerManager(),
                new PlayingException(exception.getMessage(),exception.getSeverity(),exception)));
        // Handle track exception event
    }
}