package io.lolyay.jlavalink.v4.ws.packet;

import com.google.gson.annotations.Expose;
import io.lolyay.jlavalink.JLavaLinkClient;
import io.lolyay.lavaboth.backends.lavalinkclient.player.LavaLinkPlayerManager;

import java.util.Objects;

/**
 * Abstract base class for packets that provides common functionality.
 */
public abstract class AbstractPacket implements Packet {
    private JLavaLinkClient client;

    @Override
    public JLavaLinkClient getClient(){
        return client;
    }

    public void setClient(JLavaLinkClient client){
        this.client = client;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return Objects.equals(getOpcode(), ((Packet) obj).getOpcode());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{opcode=" + getOpcode() + "}";
    }

    public String extraData;

     @Expose
     public String opcode = getOpcode();
}
