package io.lolyay.lavaboth.tracks;

import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import io.lolyay.jlavalink.v4.datatypes.ClientTrack;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.loadtracks.PlaylistLoadResult;
import io.lolyay.lavaboth.backends.lavaplayer.player.LavaPlayerPlayerManager;
import io.lolyay.lavaboth.tracks.RequestorData;


import java.util.List;

public record PlaylistData(List<MusicAudioTrack> tracks, String playlistName, int selectedTrackId) {
    public static PlaylistData fromTracksAndInfo(List<ClientTrack> tracks, PlaylistLoadResult.PlaylistInfo info, RequestorData userData) {
        return new PlaylistData(tracks.stream().map(track -> MusicAudioTrack.fromTrack(track, userData)).toList(), info.name(), info.selectedTrack());
    }

    public static PlaylistData fromTracksAndInfo(LavaPlayerPlayerManager playerManager, List<AudioTrack> tracks, AudioPlaylist info, io.lolyay.lavaboth.tracks.RequestorData userData) {
        return new PlaylistData(tracks.stream().map(track -> MusicAudioTrack.fromTrack(playerManager, track, userData)).toList(), info.getName(), tracks.indexOf(info.getSelectedTrack()));
    }

    public MusicAudioTrack selectedTrack() {
        return tracks.get(selectedTrackId());
    }

    public String name() {
        return playlistName();
    }
}
