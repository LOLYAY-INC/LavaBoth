package io.lolyay.lavaboth.backends.lavaplayer.builder;

import com.sedmelluq.discord.lavaplayer.format.AudioDataFormat;
import com.sedmelluq.discord.lavaplayer.player.AudioConfiguration;
import io.lolyay.lavaboth.backends.lavaplayer.player.LavaPlayerPlayerManager;

public class LavaPlayerBuilder {
    private AudioConfiguration.ResamplingQuality resamplingQuality = AudioConfiguration.ResamplingQuality.MEDIUM;
    private int opusEncodingQuality = 5;
    private AudioDataFormat decoderFormat;
    private boolean useGhostSeeking = true;
    private int trackStuckTimeout;


    public LavaPlayerBuilder setResamplingQuality(AudioConfiguration.ResamplingQuality resamplingQuality) {
        this.resamplingQuality = resamplingQuality;
        return this;
    }

    public LavaPlayerBuilder setOpusEncodingQuality(int opusEncodingQuality) {
        this.opusEncodingQuality = opusEncodingQuality;
        return this;
    }

    public LavaPlayerBuilder setDecoderFormat(AudioDataFormat decoderFormat) {
        this.decoderFormat = decoderFormat;
        return this;
    }

    public LavaPlayerBuilder setUseGhostSeeking(boolean useGhostSeeking) {
        this.useGhostSeeking = useGhostSeeking;
        return this;
    }

    public LavaPlayerBuilder setTrackStuckTimeout(int trackStuckTimeout) {
        this.trackStuckTimeout = trackStuckTimeout;
        return this;
    }

    public LavaPlayerPlayerManager build() {
        return new LavaPlayerPlayerManager(resamplingQuality, opusEncodingQuality, decoderFormat, useGhostSeeking, trackStuckTimeout);

    }
}
