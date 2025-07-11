package io.lolyay.jlavalink.v4.ws.packet;

/**
 * Base interface for all Server-to-Client packets.
 * These are packets sent from the server to the client.
 */
public interface S2CPacket extends Packet {
    void recivePacket();

    @Override
    default PacketType getType() {
        return PacketType.S2C;
    }
}
