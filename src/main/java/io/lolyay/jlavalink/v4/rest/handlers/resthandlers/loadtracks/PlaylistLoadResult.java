// Full Path: src/io/lolyay/jlavalink/v4/rest/handlers/resthandlers/loadtracks/PlaylistLoadResult.java
package io.lolyay.jlavalink.v4.rest.handlers.resthandlers.loadtracks;

import com.google.gson.annotations.Expose;
import io.lolyay.jlavalink.v4.datatypes.ClientTrack;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestLoadResult;

public class PlaylistLoadResult extends RestLoadResult<PlaylistLoadResult.PlaylistResultData> {
    public record PlaylistResultData(
            @Expose PlaylistInfo info,
            @Expose Object pluginInfo, // Using Object as pluginInfo can be any structure
            @Expose ClientTrack[] tracks
    ) {}

    public record PlaylistInfo(
            @Expose String name,
            @Expose int selectedTrack
    ) {}
}