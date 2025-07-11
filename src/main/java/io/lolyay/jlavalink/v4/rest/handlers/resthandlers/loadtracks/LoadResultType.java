package io.lolyay.jlavalink.v4.rest.handlers.resthandlers.loadtracks;

import com.google.gson.annotations.SerializedName;

public enum LoadResultType {
    @SerializedName("track") TRACK,
    @SerializedName("playlist") PLAYLIST,
    @SerializedName("search") SEARCH,
    @SerializedName("empty") EMPTY,
    @SerializedName("error") ERROR
}