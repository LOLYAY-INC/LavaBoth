package io.lolyay.jlavalink.v4.datatypes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Nullable;

public class ClientVersion {
        @Expose
        public Version version;
        @Expose
        public int buildTime;
        @Expose
        public Git git;
        @Expose
        @SerializedName("jvm")
        public String javaVersion;
        @Expose
        @SerializedName("lavaplayer")
        public String lavaplayerVersion;
        @Expose
        public String[] sourceManagers;
        @Expose
        public String[] filters;
        @Expose
        public Plugin[] plugins;

    private static class Version {
        @Expose
        @SerializedName("semver")
        public String fullVersionString;
        @Expose
        public String major;
        @Expose
        public String minor;
        @Expose
        public String patch;
        @Expose
        @Nullable
        public String preRelease;
        @Expose
        @Nullable
        public String build;

    }

    private static class Git {
        @Expose
        public String branch;
        @Expose
        public String commit;
        @Expose
        public int commitTime;
    }

    private static class Plugin {
        @Expose
        public String name;
        @Expose
        public String version;
    }
}
