package io.lolyay.jlavalink.v4.rest.model;

import com.google.gson.annotations.Expose;

// Represents a standard HTTP error response from Lavalink
public record RestErrorResponse(
        @Expose long timestamp,
        @Expose int status,
        @Expose String error,
        @Expose String trace,
        @Expose String message,
        @Expose String path
) implements RestResponse {}