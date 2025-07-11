package io.lolyay.jlavalink.v4.rest.handlers.resthandlers;

import com.google.gson.annotations.Expose;
import io.lolyay.jlavalink.v4.datatypes.ClientTrack;
import io.lolyay.jlavalink.v4.rest.model.RestResponse;

public class RestUpdateSessionResult implements RestResponse {
    @Expose
    public boolean resuming;
    @Expose
    public int timeout;
}
