package io.lolyay.lavaboth.tracks;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import dev.lavalink.youtube.clients.skeleton.Client;
import io.lolyay.jlavalink.v4.datatypes.ClientTrack;
import io.lolyay.lavaboth.backends.common.TrackEncodingException;
import io.lolyay.lavaboth.backends.lavaplayer.LavaPlayerTrackCoder;
import io.lolyay.lavaboth.backends.lavaplayer.player.LavaPlayerPlayerManager;
import io.lolyay.lavaboth.tracks.RequestorData;
import io.lolyay.lavaboth.tracks.TrackInfo;

import java.util.Objects;

public class MusicAudioTrack {
    private TrackInfo trackInfo;
    private String encodedTrack;
    private RequestorData userData;

    /**
     * Creates a new MusicAudioTrack from a Lavalink Track.
     *
     * @param track    The Lavalink Track to create from
     * @param userData The user who requested this track
     */
    public MusicAudioTrack(ClientTrack track, RequestorData userData) {
        this.trackInfo = new TrackInfo(
                track.getTrackInfo().getTitle(),
                track.getTrackInfo().getAuthor(),
                track.getTrackInfo().getArtworkUrl(),
                track.getTrackInfo().getIsrc()
        );
        this.userData = userData;
        this.encodedTrack = track.getEncoded();
    }


    private MusicAudioTrack() {}

    /**
     * Creates a MusicAudioTrack from a Lavaplayer AudioTrack.
     *
     * @param track    The Lavaplayer AudioTrack to create from
     * @param userData The user who requested this track
     * @return A new MusicAudioTrack instance
     */
    public static MusicAudioTrack fromTrack(LavaPlayerPlayerManager playerManager, AudioTrack track, RequestorData userData) {
        Objects.requireNonNull(track, "Track cannot be null");
        Objects.requireNonNull(userData, "User data cannot be null");

        MusicAudioTrack trackToReturn = new MusicAudioTrack();
        trackToReturn.trackInfo = new TrackInfo(
                track.getInfo().title,
                track.getInfo().author,
                track.getInfo().artworkUrl,
                track.getInfo().isrc
        );
        trackToReturn.userData = userData;

        try {
            LavaPlayerTrackCoder coder = new LavaPlayerTrackCoder(playerManager.getAudioPlayerManager());
            trackToReturn.encodedTrack = coder.encode(track);
        } catch (TrackEncodingException e) {
            throw new RuntimeException("Failed to encode track", e);
        }

        return trackToReturn;
    }



    public static MusicAudioTrack fromTrack(ClientTrack track, RequestorData userData) {
        return new MusicAudioTrack(track, userData);
    }

    public TrackInfo trackInfo() {
        return trackInfo;
    }


    public void trackInfo(TrackInfo trackInfo) {
        this.trackInfo = trackInfo;
    }


    public RequestorData userData() {
        return userData;
    }

    public void userData(RequestorData userData) {
        this.userData = userData;
    }

    public String encodedTrack() {
        return encodedTrack;
    }

    public void encodedTrack(String encodedTrack) {
        this.encodedTrack = encodedTrack;
    }
}