package io.lolyay.jlavalink.v4.datatypes;

import com.google.gson.annotations.Expose;
import org.jetbrains.annotations.NotNull;

public class ClientPlaylistInfo {
    @Expose
    @NotNull
    public String name;
    @Expose
    @NotNull
    public int selectedTrack;

    public boolean isTrackSelected(){
        return selectedTrack != -1;
    }


    public int getSelectedTrack() {
        return selectedTrack;
    }
}
