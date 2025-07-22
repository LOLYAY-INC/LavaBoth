package io.lolyay.jlavalink.v4.ws;

import io.lolyay.jlavalink.JLavaLinkClient;
import io.lolyay.jlavalink.v4.ws.packet.S2CPacket;
import io.lolyay.jlavalink.v4.ws.packet.handlers.PacketHandler;
import io.lolyay.lavaboth.utils.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Map;

public class WsClientImpl extends WebSocketClient {
    private final JLavaLinkClient client;


    public WsClientImpl(JLavaLinkClient client, URI serverUri, Map<String, String> httpHeaders) {
        super(serverUri, httpHeaders);
        this.client = client;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {

    }

    @Override
    public void onMessage(String s) {
        S2CPacket packet = PacketHandler.fromReceivedString(s);
        if (packet == null) {
            Logger.warn("Unknown packet: " + s);
            return;
        }
        packet.setClient(client);
        packet.recivePacket();

    }

    @Override
    public void onClose(int i, String s, boolean b) {
        Logger.err("Connection closed: " + s);
    }

    @Override
    public void onError(Exception e) {

    }
}
