package io.lolyay.jlavalink.data;

import java.net.URI;

public record ConnectionInfo(
        String nodeName,
        URI uri,
        boolean secure,
        String password) {
}
