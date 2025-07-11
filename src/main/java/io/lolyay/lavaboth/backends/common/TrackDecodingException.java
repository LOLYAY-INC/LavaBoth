package io.lolyay.lavaboth.backends.common;

public class TrackDecodingException extends Exception {
    public TrackDecodingException(String message, Throwable cause) {
        super(message, cause);
    }

    public TrackDecodingException(String message) {
        super(message);
    }
}