package io.lolyay.lavaboth.backends.lavaplayer.search;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import io.lolyay.lavaboth.backends.lavaplayer.player.LavaPlayerPlayerManager;
import io.lolyay.lavaboth.tracks.MusicAudioTrack;
import io.lolyay.lavaboth.tracks.PlaylistData;
import io.lolyay.lavaboth.search.Search;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class LavaResultHandler implements AudioLoadResultHandler {
    private final LavaPlayerPlayerManager playerManager;
    private final LavaPlayerSearchManager.LavaQuery searchQuery;
    private final CompletableFuture<Search> future;

    public LavaResultHandler(CompletableFuture<Search> future, LavaPlayerPlayerManager playerManager, LavaPlayerSearchManager.LavaQuery searchQuery) {
        this.future = future;
        this.searchQuery = searchQuery;
        this.playerManager = playerManager;
    }

    @Override
    public void trackLoaded(AudioTrack audioTrack) {
        MusicAudioTrack musicAudioTrack = MusicAudioTrack.fromTrack(playerManager, audioTrack, searchQuery.getRequestorData());

        future.complete(Search.wasTrack(
                Search.SearchResult.SUCCESS(), searchQuery.getSource().getSourceName(),
                searchQuery.getQuery(),
                new ArrayList<>(List.of(musicAudioTrack))
        ));
    }

    @Override
    public void playlistLoaded(AudioPlaylist audioPlaylist) {
        if (audioPlaylist.isSearchResult()) {
            trackSearch(audioPlaylist);
            return;
        }

            PlaylistData playlistData = PlaylistData.fromTracksAndInfo(playerManager ,audioPlaylist.getTracks(), audioPlaylist, searchQuery.getRequestorData());


        future.complete(Search.wasPlaylist(
                    Search.SearchResult.PLAYLIST(),
                    searchQuery.getSource().getSourceName(),
                    searchQuery.getQuery(),
                    playlistData
            ));

    }

    @Override
    public void noMatches() {
        future.complete(Search.wasNotFound(
                Search.SearchResult.NOT_FOUND(),
                searchQuery.getSource().getSourceName(),
                searchQuery.getQuery()
        ));
    }

    @Override
    public void loadFailed(FriendlyException e) {
        future.complete(Search.wasError(
                Search.SearchResult.ERROR(e.getMessage()),
                searchQuery.getSource().getSourceName(),
                searchQuery.getQuery()
        ));
    }

    private void trackSearch(AudioPlaylist audioPlaylist) {
        ArrayList<MusicAudioTrack> trackList = new ArrayList<>();
        for (AudioTrack track : audioPlaylist.getTracks()) {
            trackList.add(MusicAudioTrack.fromTrack(playerManager, track, searchQuery.getRequestorData()));
        }

        future.complete(Search.wasTrack(
                Search.SearchResult.SUCCESS(), searchQuery.getSource().getSourceName(),
                searchQuery.getQuery(),
                trackList
        ));

    }
}