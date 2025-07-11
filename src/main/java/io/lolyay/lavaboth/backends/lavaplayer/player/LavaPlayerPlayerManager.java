package io.lolyay.lavaboth.backends.lavaplayer.player;

import com.sedmelluq.discord.lavaplayer.format.AudioDataFormat;
import com.sedmelluq.discord.lavaplayer.player.AudioConfiguration;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import io.lolyay.lavaboth.backends.common.AbstractPlayerManager;
import io.lolyay.lavaboth.backends.lavaplayer.search.LavaPlayerSearchManager;
import io.lolyay.lavaboth.backends.lavaplayer.builder.LavaPlayerBuilder;

public class LavaPlayerPlayerManager extends AbstractPlayerManager {
    private final AudioPlayerManager audioPlayerManager;
    private final LavaPlayerFactory playerFactory;
    private final LavaPlayerSearchManager searchManager;


    public LavaPlayerPlayerManager(AudioConfiguration.ResamplingQuality resamplingQuality,
                                   int opusEncodingQuality,
                                   AudioDataFormat decoderFormat,
                                   boolean useGhostSeeking,
                                   int trackStuckTimeout) {
        audioPlayerManager = new DefaultAudioPlayerManager();
        audioPlayerManager.getConfiguration().setResamplingQuality(resamplingQuality);
        audioPlayerManager.getConfiguration().setOpusEncodingQuality(opusEncodingQuality);
        if(decoderFormat != null)
            audioPlayerManager.getConfiguration().setOutputFormat(decoderFormat);
        audioPlayerManager.setUseSeekGhosting(useGhostSeeking);
        if(trackStuckTimeout > 0)
            audioPlayerManager.setTrackStuckThreshold(trackStuckTimeout);
        playerFactory = new LavaPlayerFactory(this);
        searchManager = new LavaPlayerSearchManager(this);

    }

    @Override
    public LavaPlayerFactory getPlayerFactory() {
        return playerFactory;
    }

    @Override
    public LavaPlayerSearchManager getSearchManager() {
        return searchManager;
    }



    public static LavaPlayerBuilder getBuilder() {
        return new LavaPlayerBuilder();
    }

    public AudioPlayerManager getAudioPlayerManager() {
        return audioPlayerManager;
    }

}
