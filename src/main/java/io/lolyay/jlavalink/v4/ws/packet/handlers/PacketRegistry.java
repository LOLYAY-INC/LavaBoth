package io.lolyay.jlavalink.v4.ws.packet.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.lolyay.jlavalink.v4.ws.packet.AbstractPacket;
import io.lolyay.jlavalink.v4.ws.packet.Packet;
import io.lolyay.jlavalink.v4.ws.packet.S2CPacket;


import java.util.HashMap;
import java.util.Map;

/**
 * Registry for managing packet types and their corresponding classes.
 */
public class PacketRegistry {
    private static final Map<String, Class<S2CPacket>> packetMap = new HashMap<>();
    private static final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    /**
     * Registers a packet class with the registry.
     * @param packetClass The packet class to register
     * @throws IllegalStateException if a packet with the same opcode is already registered
     */
    public static void registerPacket(Class<S2CPacket> packetClass) {
        try {
            // Create an instance to get the opcode
            Packet packet = packetClass.getDeclaredConstructor().newInstance();
            String opcode = packet.getOpcode();
            
            if (packetMap.containsKey(opcode)) {
                throw new IllegalStateException("Packet with opcode " + opcode + " is already registered by " + 
                        packetMap.get(opcode).getName());
            }
            
            packetMap.put(opcode, packetClass);
        } catch (Exception e) {
            throw new RuntimeException("Failed to register packet: " + packetClass.getName(), e);
        }
    }

    /**
     * Creates a packet instance from the given opcode and JSON data.
     * @param opcode The opcode of the packet
     * @param jsonData The JSON data to deserialize
     * @return A new packet instance, or null if no packet is registered for the opcode
     */
    public static S2CPacket createPacket(String opcode, String jsonData) {
        try {
            Class<S2CPacket> packetClass = packetMap.get(opcode);
            if (packetClass == null) {
                return null;
            }
            S2CPacket packet = gson.fromJson(jsonData, packetClass);

            ((AbstractPacket) packet).extraData = jsonData;
            
            return packet;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create packet for opcode: " + opcode, e);
        }
    }

    /**
     * Gets the packet class for the given opcode.
     * @param opcode The opcode to look up
     * @return The packet class, or null if not found
     */
    public static Class<S2CPacket> getPacketClass(String opcode) {
        return packetMap.get(opcode);
    }

    /**
     * Creates a new instance of the packet with the given opcode.
     * @param opcode The opcode of the packet to create
     * @return A new packet instance, or null if no packet is registered for the opcode
     */
    public static Packet createPacket(String opcode) {
        try {
            Class<S2CPacket> packetClass = packetMap.get(opcode);
            if (packetClass == null) {
                return null;
            }
            return packetClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create packet for opcode: " + opcode, e);
        }
    }
}
