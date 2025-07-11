package io.lolyay.jlavalink.v4.datatypes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.lolyay.lavaboth.tracks.RequestorData;

import java.util.Map;

public class ClientTrack {
    @Expose
    private String encoded;

    @Expose
    private RequestorData userData;

    @Expose
    private Map<String, String> pluginInfo;

    @SerializedName("info")
    @Expose
    private TrackInfo trackInfo;

    public static class TrackInfo{
        @Expose
        private String identifier;
        @Expose
        private String author;
        @Expose
        private String title;
        @Expose
        private boolean isSeekable;
        @Expose
        private int length;
        @Expose
        private boolean isStream;
        @Expose
        private int position;
        @Expose
        private String uri;
        @Expose
        private String artworkUrl;
        @Expose
        private String isrc;
        @Expose
        private String sourceName;

        public boolean isSeekable() {
            return isSeekable;
        }

        public String getIdentifier() {
            return identifier;
        }

        public String getAuthor() {
            return author;
        }

        public String getTitle() {
            return title;
        }

        public int getLength() {
            return length;
        }

        public boolean isStream() {
            return isStream;
        }

        public int getPosition() {
            return position;
        }

        public String getUri() {
            return uri;
        }

        public String getArtworkUrl() {
            return artworkUrl;
        }

        public String getIsrc() {
            return isrc;
        }

        public String getSourceName() {
            return sourceName;
        }
    }

    public static class PositionableTrackInfo extends TrackInfo{
        @Expose
        private String identifier;
        @Expose
        private String author;
        @Expose
        private String title;
        @Expose
        private boolean isSeekable;
        @Expose
        private int length;
        @Expose
        private boolean isStream;
        @Expose
        private int position;
        @Expose
        private String uri;
        @Expose
        private String artworkUrl;
        @Expose
        private String isrc;
        @Expose
        private String sourceName;

        public boolean isSeekable() {
            return isSeekable;
        }

        public String getIdentifier() {
            return identifier;
        }

        public String getAuthor() {
            return author;
        }

        public String getTitle() {
            return title;
        }

        public int getLength() {
            return length;
        }

        public boolean isStream() {
            return isStream;
        }

        public int getPosition() {
            return position;
        }

        public String getUri() {
            return uri;
        }

        public String getArtworkUrl() {
            return artworkUrl;
        }

        public String getIsrc() {
            return isrc;
        }

        public String getSourceName() {
            return sourceName;
        }
    }

    public String getEncoded() {
        return encoded;
    }

    public RequestorData getUserData() {
        return userData;
    }

    public Map<String, String> getPluginInfo() {
        return pluginInfo;
    }

    public TrackInfo getTrackInfo() {
        return trackInfo;
    }
}
