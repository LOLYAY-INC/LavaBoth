package io.lolyay.jlavalink.v4.datatypes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public enum ClientTrackEndReason {
    @SerializedName("finished")
    @Expose
    FINISHED(true),
    @Expose
    @SerializedName("loadFailed")
    LOAD_FAILED(true),
    @Expose
    @SerializedName("stopped")
    STOPPED(false),
    @Expose
    @SerializedName("replaced")
    REPLACED(false),
    @Expose
    @SerializedName("cleanup")
    CLEANUP(false);

    private boolean mayStartNext = false;

    public boolean mayStartNext() {
        return mayStartNext;
    }

    ClientTrackEndReason(boolean mayStartNext) {
        this.mayStartNext = mayStartNext;
    }
}
