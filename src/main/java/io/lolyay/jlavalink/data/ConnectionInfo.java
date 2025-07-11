package io.lolyay.jlavalink.data;

import java.net.URI;

public record ConnectionInfo(
        String nodeName,
        URI uri,
        int port,
        boolean secure,
        String password) {
    public static ConnectionInfo of(String host, int port, boolean secure, String password) {
        return new ConnectionInfo(null, createUri(host, port, secure), port, secure, password);
    }

    private static URI createUri(String host, int port, boolean secure) {
        return URI.create(String.format("%s://%s:%s", secure ? "wss" : "ws", host, port));
    }
}
