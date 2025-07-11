package io.lolyay.jlavalink.v4.ws.packet.packets.S2C;

import com.google.gson.annotations.Expose;
import io.lolyay.jlavalink.v4.ws.packet.AbstractPacket;
import io.lolyay.jlavalink.v4.ws.packet.S2CPacket;
import io.lolyay.lavaboth.utils.Logger;

public class S2CReadyPacket extends AbstractPacket implements S2CPacket {
    @Expose
    boolean resumed;
    @Expose
    String sessionId;

    @Override
    public void recivePacket() {
        Logger.success("Ready packet received (" + sessionId + ")");
        getClient().setSessionId(sessionId);
    }

    @Override
    public String getOpcode() {
        return "ready";
    }
}
