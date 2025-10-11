package io.lolyay.lavaboth.backends.lavaplayer.sourceshelper;

import com.github.topi314.lavasrc.applemusic.AppleMusicSourceManager;
import com.github.topi314.lavasrc.deezer.DeezerAudioSourceManager;
import com.github.topi314.lavasrc.deezer.DeezerAudioTrack;
import com.github.topi314.lavasrc.mirror.DefaultMirroringAudioTrackResolver;
import com.github.topi314.lavasrc.spotify.SpotifySourceManager;
import com.github.topi314.lavasrc.tidal.TidalSourceManager;
import com.github.topi314.lavasrc.ytdlp.YtdlpAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.container.MediaContainerRegistry;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.bandcamp.BandcampAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.beam.BeamAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.getyarn.GetyarnAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.http.HttpAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.nico.NicoAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.soundcloud.SoundCloudAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.twitch.TwitchStreamAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.vimeo.VimeoAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.yamusic.YandexMusicAudioSourceManager;
import dev.lavalink.youtube.YoutubeAudioSourceManager;
import dev.lavalink.youtube.YoutubeSourceOptions;
import dev.lavalink.youtube.clients.*;
import io.lolyay.lavaboth.LavaBoth;
import io.lolyay.lavaboth.backends.lavaplayer.player.LavaPlayerPlayerManager;
import io.lolyay.lavaboth.utils.Logger;

import java.util.ArrayList;
import java.util.function.Function;

public class SourcesBuilder {
    private final ArrayList<AudioSourceManager> sources = new ArrayList<>();
    private final LavaPlayerPlayerManager playerManager;
    private final Function<Void, AudioPlayerManager> sourceManagerSupplier;

    public SourcesBuilder(LavaPlayerPlayerManager playerManager) {
        this.playerManager = playerManager;
        sourceManagerSupplier = pm -> playerManager.getAudioPlayerManager();
    }


    public SourcesBuilder addYoutube(String oauthToken, YoutubeSourceOptions options) {
        boolean skipInit = !oauthToken.isEmpty();
        YoutubeAudioSourceManager source;
        if(options!= null)
            source = new YoutubeAudioSourceManager(
                    options,
                    new MusicWithThumbnail(),
                    new TvHtml5EmbeddedWithThumbnail(),
                    new AndroidVrWithThumbnail(),
                    new WebWithThumbnail(),
                    new WebEmbeddedWithThumbnail(),
                    new MWebWithThumbnail(),
                    new AndroidMusicWithThumbnail()
            );
        else {
            Logger.err("No YoutubeSourceOptions provided, there may be errors during playback, refer to https://github.com/lavalink-devs/youtube-source?tab=readme-ov-file#using-a-remote-cipher-server");
            source = new YoutubeAudioSourceManager(
                    new YoutubeSourceOptions().setRemoteCipherUrl(LavaBoth.defaultRemoteCipheringServer, null),
                    new MusicWithThumbnail(),
                    new TvHtml5EmbeddedWithThumbnail(),
                    new AndroidVrWithThumbnail(),
                    new WebWithThumbnail(),
                    new WebEmbeddedWithThumbnail(),
                    new MWebWithThumbnail(),
                    new AndroidMusicWithThumbnail()
            );
        }

        source.useOauth2(oauthToken, skipInit);
        sources.add(source);
        return this;
    }

    public SourcesBuilder addYoutubeDlp(String youtubeDlpPath) {
        YtdlpAudioSourceManager source = new YtdlpAudioSourceManager(
                youtubeDlpPath
        );
        sources.add(source);
        return this;
    }
    
    public SourcesBuilder addDefault(){
        sources.add(new YandexMusicAudioSourceManager(true));
        sources.add(SoundCloudAudioSourceManager.createDefault());
        sources.add(new BandcampAudioSourceManager());
        sources.add(new VimeoAudioSourceManager());
        sources.add(new TwitchStreamAudioSourceManager());
        sources.add(new BeamAudioSourceManager());
        sources.add(new GetyarnAudioSourceManager());
        sources.add(new NicoAudioSourceManager());
        sources.add(new HttpAudioSourceManager(MediaContainerRegistry.DEFAULT_REGISTRY));
        return this;
    }

    public SourcesBuilder setupSpotify(String clientId, String clientSecret,String spdcCookie ,String countryCode) {
        SpotifySourceManager spotifySourceManager = new SpotifySourceManager(
                clientId,
                clientSecret,
                spdcCookie,
                countryCode,
                sourceManagerSupplier,
                new DefaultMirroringAudioTrackResolver(new String[]{})
        );
        sources.add(spotifySourceManager);
        return this;
    }

    public SourcesBuilder setupAppleMusic(String appleMusicToken,String countryCode) {
        AppleMusicSourceManager appleMusicSourceManager = new AppleMusicSourceManager(
                null,
                appleMusicToken,
                countryCode,
                sourceManagerSupplier
        );
        sources.add(appleMusicSourceManager);
        return this;
    }


    public SourcesBuilder setupDeezer(String deezerDecryptionKey,String deezerArlCookie) {
        DeezerAudioSourceManager deezerSourceManager = new DeezerAudioSourceManager(
                deezerDecryptionKey,
                deezerArlCookie,
                DeezerAudioTrack.TrackFormat.DEFAULT_FORMATS
        );
        sources.add(deezerSourceManager);
        return this;
    }

    public SourcesBuilder setupTidal(String tidalToken,String countryCode) {
        TidalSourceManager tidalSourceManager = new TidalSourceManager(
                countryCode,
                sourceManagerSupplier,
                new DefaultMirroringAudioTrackResolver(new String[]{}),
                tidalToken
        );
        sources.add(tidalSourceManager);
        return this;
    }

    public void buildAndRegister() {
        for (AudioSourceManager source : sources) {
            playerManager.getAudioPlayerManager().registerSourceManager(source);
        }
    }


}
