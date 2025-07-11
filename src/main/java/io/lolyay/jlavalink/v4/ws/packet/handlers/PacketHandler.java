package io.lolyay.jlavalink.v4.ws.packet.handlers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.lolyay.jlavalink.v4.ws.packet.S2CPacket;

import java.util.Map;

public class PacketHandler {

    public static S2CPacket fromReceivedString(String s) {
        //  Logger.warn(s);

        try {
            Map<String, Object> parsedMap = new Gson().fromJson(s, Map.class);
            if (!parsedMap.containsKey("op") || parsedMap.get("op") == null) {
                return null;
            }
            return PacketRegistry.createPacket((String) parsedMap.get("op"),s);
        } catch (JsonSyntaxException e) {
        }
        return null;
        // UNKNOWN PACKET
    }
}
