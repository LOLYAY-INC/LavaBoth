package io.lolyay.jlavalink.v4.rest.handlers.resthandlers;

import com.google.gson.annotations.Expose;
import io.lolyay.jlavalink.v4.datatypes.ClientPlayer;
import io.lolyay.jlavalink.v4.datatypes.ClientTrack;
import io.lolyay.jlavalink.v4.rest.model.RestResponse;

public class RestAllPlayersResult implements RestResponse {
    @Expose
    public ClientPlayer[] players;
}
