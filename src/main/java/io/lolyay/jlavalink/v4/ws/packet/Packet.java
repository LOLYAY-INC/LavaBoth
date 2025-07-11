package io.lolyay.jlavalink.v4.ws.packet;

import com.google.gson.ExclusionStrategy;
import com.google.gson.GsonBuilder;
import io.lolyay.jlavalink.JLavaLinkClient;
import io.lolyay.lavaboth.backends.lavalinkclient.player.LavaLinkPlayerManager;

public interface Packet {
    JLavaLinkClient getClient();
    void setClient(JLavaLinkClient client);

    /**
     * @return The unique opcode that identifies this packet type
     */
    String getOpcode();
    
    /**
     * @return The type of this packet (C2S for Client-to-Server, S2C for Server-to-Client)
     */
    default PacketType getType() {
        return PacketType.UNKNOWN;
    }

    default String getJSON() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create().toJson(this);
    }
    
    enum PacketType {
        C2S,  // Client to Server
        S2C,  // Server to Client
        UNKNOWN
    }
}
