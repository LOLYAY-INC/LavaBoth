package io.lolyay.lavaboth.exceptions;

import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import io.lolyay.jlavalink.v4.datatypes.ClientException;

public class PlayingException extends FriendlyException {
    public PlayingException(String friendlyMessage, ClientException.Severity severity, Throwable cause) {
        super(friendlyMessage, Severity.valueOf(severity.name()), cause);
    }
}
