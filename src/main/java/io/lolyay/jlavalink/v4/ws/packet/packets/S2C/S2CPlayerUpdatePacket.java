package io.lolyay.jlavalink.v4.ws.packet.packets.S2C;

import com.google.gson.annotations.Expose;
import io.lolyay.jlavalink.v4.datatypes.ClientPlayer;
import io.lolyay.jlavalink.v4.ws.packet.AbstractPacket;
import io.lolyay.jlavalink.v4.ws.packet.S2CPacket;
import io.lolyay.lavaboth.utils.Logger;

public class S2CPlayerUpdatePacket extends AbstractPacket implements S2CPacket {
    @Expose
    public String guildId;
    @Expose
    public ClientPlayer.PlayerState state;


    @Override
    public void recivePacket() {
        Logger.log("Player update packet received");
        ClientPlayer player = getClient().getClientPlayerFactory().getOrCreatePlayer(guildId);
        player.playerState = state;
    }

    @Override
    public String getOpcode() {
        return "playerUpdate";
    }

}
