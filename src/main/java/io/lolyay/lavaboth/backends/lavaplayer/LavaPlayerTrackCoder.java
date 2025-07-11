package io.lolyay.lavaboth.backends.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.io.ByteBufferInputStream;
import com.sedmelluq.discord.lavaplayer.tools.io.MessageInput;
import com.sedmelluq.discord.lavaplayer.tools.io.MessageOutput;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import io.lolyay.lavaboth.backends.common.TrackDecodingException;
import io.lolyay.lavaboth.backends.common.TrackEncodingException;
import io.lolyay.lavaboth.tracks.TrackEncodeUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;

public class LavaPlayerTrackCoder{
    private static final String FORMAT = "lavaplayer";
    private final AudioPlayerManager audioPlayerManager;

    public LavaPlayerTrackCoder(AudioPlayerManager audioPlayerManager) {
        this.audioPlayerManager = Objects.requireNonNull(audioPlayerManager, "audioPlayerManager cannot be null");
    }

    public String encode(AudioTrack track) throws TrackEncodingException {
        if (track == null) {
            throw new TrackEncodingException("Cannot encode null track");
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            audioPlayerManager.encodeTrack(new MessageOutput(outputStream), track);
            return TrackEncodeUtils.encodeToBase64(outputStream.toByteArray());
        } catch (IOException e) {
            throw new TrackEncodingException("Failed to encode audio track", e);
        }
    }

    public AudioTrack decode(String encodedTrack) throws TrackDecodingException {
        if (encodedTrack == null || encodedTrack.isBlank()) {
            throw new TrackDecodingException("Encoded track cannot be null or blank");
        }

        try {
            byte[] data = TrackEncodeUtils.decodeFromBase64(encodedTrack);
            var message = audioPlayerManager.decodeTrack(
                    new MessageInput(new ByteBufferInputStream(ByteBuffer.wrap(data)))
            );

            if (message == null) {
                throw new TrackDecodingException("Failed to decode track - null message");
            }

            return message.decodedTrack;
        } catch (IOException e) {
            throw new TrackDecodingException("Failed to decode audio track", e);
        }
    }

    public String getFormat() {
        return FORMAT;
    }
}