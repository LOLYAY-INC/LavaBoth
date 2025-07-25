package io.lolyay.jlavalink.v4.ws.packet.packets.S2C;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.lolyay.jlavalink.v4.datatypes.ClientStats;
import io.lolyay.jlavalink.v4.ws.packet.AbstractPacket;
import io.lolyay.jlavalink.v4.ws.packet.S2CPacket;
import io.lolyay.lavaboth.utils.Logger;

public class S2CStatsPacket extends AbstractPacket implements S2CPacket {

    @Expose
    public int players;

    @Expose
    public int playingPlayers;

    @SerializedName("uptime")
    @Expose
    public long upTime;

    @Expose
    public Memory memory;

    @Expose
    public CPU cpu;

    @Expose
    public FrameStats frameStats;


    @Override
    public void recivePacket() {
        Logger.log("Stats packet received");
        ClientStats stats = new ClientStats();
        stats.players = players;
        stats.playingPlayers = playingPlayers;
        stats.upTime = upTime;
        stats.memory = memory;
        stats.cpu = cpu;
        stats.frameStats = frameStats;
        getClient().setNodeStats(stats);
    }

    @Override
    public String getOpcode() {
        return "stats";
    }

    public static class Memory {
        @Expose
        long free;
        @Expose
        long used;
        @Expose
        long allocated;
        @Expose
        long reservable;
    }

    public static class CPU {
        @Expose
        int cores;
        @Expose
        long systemLoad;
        @Expose
        long lavalinkLoad;
    }

    public static class FrameStats {
        @Expose
        long sent;
        @Expose
        long nulled;
        @Expose
        long deficit;
    }
}
