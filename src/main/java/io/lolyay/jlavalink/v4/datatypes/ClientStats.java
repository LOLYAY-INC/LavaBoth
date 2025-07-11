package io.lolyay.jlavalink.v4.datatypes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.lolyay.jlavalink.v4.ws.packet.packets.S2C.S2CStatsPacket;

public class ClientStats {
    @Expose
    public int players;

    @Expose
    public int playingPlayers;

    @SerializedName("uptime")
    @Expose
    public long upTime;

    @Expose
    public S2CStatsPacket.Memory memory;

    @Expose
    public S2CStatsPacket.CPU cpu;

    @Expose
    public S2CStatsPacket.FrameStats frameStats;
}
