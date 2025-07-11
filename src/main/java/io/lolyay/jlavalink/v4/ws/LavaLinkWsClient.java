package io.lolyay.jlavalink.v4.ws;

import io.lolyay.jlavalink.JLavaLinkClient;
import io.lolyay.jlavalink.data.JLavaLinkClientInfo;
import io.lolyay.jlavalink.data.ConnectionInfo;
import io.lolyay.jlavalink.v4.ws.packet.C2SPacket;
import io.lolyay.lavaboth.utils.Logger;
import org.java_websocket.client.WebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


public class LavaLinkWsClient {
    private WebSocketClient webSocketClient;
    private final JLavaLinkClientInfo clientInfo;
    private final JLavaLinkClient jLavaLinkClient;

    public LavaLinkWsClient(JLavaLinkClient jLavaLinkClient) {
        this.jLavaLinkClient = jLavaLinkClient;
        this.clientInfo = jLavaLinkClient.getClientInfo();
    }

    public void init() {
        webSocketClient = new WsClientImpl(
                jLavaLinkClient,
                URI.create(clientInfo.connection().uri().toString() + "/v4/websocket"),
                getHeaders()
        );
        webSocketClient.connect();
        Logger.success("Connected to Lavalink!");

    }

    public void sendPacket(C2SPacket packet){
        webSocketClient.send(packet.getJSON());
    }


    private Map<String ,String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", clientInfo.connection().password());
        headers.put("User-Id", String.valueOf(clientInfo.botUserId()));
        headers.put("Client-Name", clientInfo.clientName());
        return headers;
    }

    private String getConProtocol(ConnectionInfo conInfo) {
        return conInfo.secure() ? "wss://" : "ws://";
    }
}
