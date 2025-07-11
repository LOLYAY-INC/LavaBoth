package io.lolyay.lavaboth.backends.lavaplayer.search;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import io.lolyay.lavaboth.backends.lavaplayer.player.LavaPlayerPlayerManager;
import io.lolyay.lavaboth.tracks.MusicAudioTrack;
import io.lolyay.lavaboth.tracks.PlaylistData;
import io.lolyay.lavaboth.search.Search;

import java.util.function.Consumer;

public class LavaResultHandler implements AudioLoadResultHandler {
    private final Consumer<Search> callback;
    private final LavaPlayerPlayerManager playerManager;
    private final LavaPlayerSearchManager.LavaQuery searchQuery;

    public LavaResultHandler(Consumer<Search> callback, LavaPlayerPlayerManager playerManager, LavaPlayerSearchManager.LavaQuery searchQuery) {
        this.callback = callback;
        this.searchQuery = searchQuery;
        this.playerManager = playerManager;
    }

    @Override
    public void trackLoaded(AudioTrack audioTrack) {
        MusicAudioTrack musicAudioTrack = MusicAudioTrack.fromTrack(playerManager, audioTrack, searchQuery.getRequestorData());

        callback.accept(Search.wasTrack(
                Search.SearchResult.SUCCESS(), searchQuery.getSource().getSourceName(),
                searchQuery.getQuery(),
                musicAudioTrack
        ));
    }

    @Override
    public void playlistLoaded(AudioPlaylist audioPlaylist) {
        if (audioPlaylist.isSearchResult()) {
            trackSearch(audioPlaylist);
            return;
        }

            PlaylistData playlistData = PlaylistData.fromTracksAndInfo(playerManager ,audioPlaylist.getTracks(), audioPlaylist, searchQuery.getRequestorData());


            callback.accept(Search.wasPlaylist(
                    Search.SearchResult.PLAYLIST(),
                    searchQuery.getSource().getSourceName(),
                    searchQuery.getQuery(),
                    playlistData
            ));

    }

    @Override
    public void noMatches() {
        callback.accept(Search.wasNotFound(
                Search.SearchResult.NOT_FOUND(),
                searchQuery.getSource().getSourceName(),
                searchQuery.getQuery()
        ));
    }

    @Override
    public void loadFailed(FriendlyException e) {
        callback.accept(Search.wasError(
                Search.SearchResult.ERROR(e.getMessage()),
                searchQuery.getSource().getSourceName(),
                searchQuery.getQuery()
        ));
    }

    private void trackSearch(AudioPlaylist audioPlaylist) {
        //TODO: MULTIPLE SEARCH
        trackLoaded(audioPlaylist.getSelectedTrack() == null ? audioPlaylist.getTracks().getFirst() : audioPlaylist.getSelectedTrack());
    }



}