package io.lolyay.jlavalink.v4.datatypes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ClientException extends Throwable {
    @Expose
    @Nullable
    private String message;

    @Expose
    @NotNull
    private Severity severity;

    @Expose
    @NotNull
    private String cause;

    @Expose
    @NotNull
    private String causeStackTrace;


    public @Nullable String getMessage() {
        return message;
    }

    public @NotNull Severity getSeverity() {
        return severity;
    }

    public @NotNull String getCauseStr() {
        return cause;
    }

    public @NotNull String getCauseStackTrace() {
        return causeStackTrace;
    }

    public enum Severity {
        @Expose
        @SerializedName("common")
        COMMON,
        @Expose
        @SerializedName("suspicious")
        SUSPICIOUS,
        @Expose
        @SerializedName("fault")
        FAULT
    }
}
