package io.lolyay.jlavalink.v4.ws.packet;

/**
 * Base interface for all Client-to-Server packets.
 * These are packets sent from the client to the server.
 */
public interface C2SPacket extends Packet {

    @Override
    default PacketType getType() {
        return PacketType.C2S;
    }
}
