package io.lolyay.jlavalink.v4.ws.packet.packets.S2C.event;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.lolyay.jlavalink.v4.datatypes.ClientTrack;
import io.lolyay.jlavalink.v4.ws.packet.AbstractPacket;
import io.lolyay.jlavalink.v4.ws.packet.S2CPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// S2CBaseEventPacket.java
public class S2CBaseEventPacket extends AbstractPacket implements S2CPacket {

    private static final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    @NotNull
    @Expose
    public String guildId;

    @NotNull
    @Expose
    @SerializedName("type")
    public EventType eventType;

    @Nullable
    @Expose
    public ClientTrack track;


    @Override
    public String getOpcode() {
        return "event";
    }

    @Override
    public void recivePacket(){
        S2CBaseEventPacket packet;
        switch (eventType){
            case TrackEndEvent -> packet = gson.fromJson(extraData, TrackEndEvent.class);
            case TrackStartEvent -> packet = gson.fromJson(extraData, TrackStartEvent.class);
            case TrackExceptionEvent -> packet = gson.fromJson(extraData, TrackExceptionEvent.class);
            case TrackStuckEvent -> packet = gson.fromJson(extraData, TrackStuckEvent.class);
            case WebSocketClosedEvent -> packet = gson.fromJson(extraData, WebSocketClosedEvent.class);
            default -> throw new IllegalStateException("Unexpected value: " + eventType);
        }
        packet.setClient(getClient());
        packet.recivePacket();
    }
}