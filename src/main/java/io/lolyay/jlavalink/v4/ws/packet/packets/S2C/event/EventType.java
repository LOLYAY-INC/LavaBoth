package io.lolyay.jlavalink.v4.ws.packet.packets.S2C.event;

import com.google.gson.annotations.Expose;
import io.lolyay.jlavalink.v4.ws.packet.handlers.NoPacket;


@NoPacket
public enum EventType {
    @Expose
    TrackStartEvent,
    @Expose
    TrackEndEvent,
    @Expose
    TrackExceptionEvent,
    @Expose
    TrackStuckEvent,
    @Expose
    WebSocketClosedEvent
}