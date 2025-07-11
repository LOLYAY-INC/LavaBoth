package io.lolyay.lavaboth.backends.lavalinkclient;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.io.ByteBufferInputStream;
import com.sedmelluq.discord.lavaplayer.tools.io.MessageInput;
import com.sedmelluq.discord.lavaplayer.tools.io.MessageOutput;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import io.lolyay.jlavalink.JLavaLinkClient;
import io.lolyay.jlavalink.v4.datatypes.ClientTrack;
import io.lolyay.lavaboth.backends.common.TrackDecodingException;
import io.lolyay.lavaboth.backends.common.TrackEncodingException;
import io.lolyay.lavaboth.tracks.TrackEncodeUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;

public class LavaLinkTrackCoder {
    private static final String FORMAT = "lavalink";
    private final JLavaLinkClient lavaLinkClient;

    public LavaLinkTrackCoder(JLavaLinkClient lavaLinkClient) {
        this.lavaLinkClient = Objects.requireNonNull(lavaLinkClient, "audioPlayerManager cannot be null");
    }

    public String encode(ClientTrack track) throws TrackEncodingException {
        return track.getEncoded();
    }

    public ClientTrack decode(String encodedTrack) throws TrackDecodingException {
        return lavaLinkClient.decodeTrack(encodedTrack).join();
    }

    public String getFormat() {
        return FORMAT;
    }
}