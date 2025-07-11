package io.lolyay.lavaboth.tracks;

import java.util.Base64;

public class TrackEncodeUtils {
    public static byte[] decodeFromBase64(String encoded) {
        if (encoded == null) {
            throw new IllegalArgumentException("Input String cannot be null.");
        }
        return Base64.getDecoder().decode(encoded);
    }

    public static String encodeToBase64(byte[] input) {
        if (input == null) {
            throw new IllegalArgumentException("Input byte array cannot be null.");
        }
        return Base64.getEncoder().encodeToString(input);
    }
}
