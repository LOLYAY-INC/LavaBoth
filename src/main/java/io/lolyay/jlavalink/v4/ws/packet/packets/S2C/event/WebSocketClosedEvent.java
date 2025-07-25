package io.lolyay.jlavalink.v4.ws.packet.packets.S2C.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.lolyay.jlavalink.v4.ws.packet.handlers.NoPacket;
import io.lolyay.lavaboth.utils.Logger;

@NoPacket
public class WebSocketClosedEvent extends S2CBaseEventPacket {
    @Expose
    @SerializedName("code")
    public int discordCloseCode;

    @Expose
    @SerializedName("reason")
    public String discordCloseReason;

    @Expose
    public boolean byRemote;


    public void recivePacket() {
        if(!getClient().getClientPlayerFactory().getOrCreatePlayer(guildId).shouldBePlaying)
            return;

        Logger.err("#######"); // exclamation mark
        Logger.err("#     #");
        Logger.err("#     #");
        Logger.err("#     #");
        Logger.err("#     #");
        Logger.err("#     #");
        Logger.err("#     #");
        Logger.err("#######");
        Logger.err("");
        Logger.err("");
        Logger.err("#######");
        Logger.err("#######");
        Logger.err("#######");

        Logger.err("Websocket closed with code: " + discordCloseCode + " (" + discordCloseReason + ")");
        Logger.err("By remote: " + byRemote);
        Logger.err("Need to restart all audio players");
        Logger.err("This is an issue with your lavalink node and not LavaBoth");


    }
}
