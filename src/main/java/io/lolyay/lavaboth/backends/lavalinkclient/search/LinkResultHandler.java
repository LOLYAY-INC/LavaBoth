package io.lolyay.lavaboth.backends.lavalinkclient.search;

import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import io.lolyay.jlavalink.v4.datatypes.ClientException;
import io.lolyay.jlavalink.v4.datatypes.ClientTrack;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.RestLoadResult;
import io.lolyay.jlavalink.v4.rest.handlers.resthandlers.loadtracks.*;
import io.lolyay.lavaboth.backends.lavalinkclient.player.LavaLinkPlayerManager;
import io.lolyay.lavaboth.search.Search;
import io.lolyay.lavaboth.tracks.MusicAudioTrack;
import io.lolyay.lavaboth.tracks.PlaylistData;
import io.lolyay.lavaboth.utils.Logger;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class LinkResultHandler implements Consumer<RestLoadResult<?>> {
    private final CompletableFuture<Search> callback;
    private final LavaLinkPlayerManager playerManager;
    private final LavaLinkSearchManager.LinkQuery searchQuery;

    public LinkResultHandler(CompletableFuture<Search> callback, LavaLinkPlayerManager playerManager, LavaLinkSearchManager.LinkQuery searchQuery) {
        this.callback = callback;
        this.searchQuery = searchQuery;
        this.playerManager = playerManager;
    }


    public void trackLoaded(ClientTrack clientTrack) {
        MusicAudioTrack musicAudioTrack = MusicAudioTrack.fromTrack(clientTrack, searchQuery.getRequestorData());

        callback.complete(Search.wasTrack(
                Search.SearchResult.SUCCESS(), searchQuery.getSource().getSourceName(),
                searchQuery.getQuery(),
                new ArrayList<>(List.of(musicAudioTrack))
        ));
    }


    public void playlistLoaded(PlaylistLoadResult playlistLoadResult) {
        PlaylistData playlistData = new PlaylistData(
                Arrays.stream(playlistLoadResult.data.tracks()).map(track -> MusicAudioTrack.fromTrack(track, searchQuery.getRequestorData())).toList(),
                playlistLoadResult.data.info().name(),
                playlistLoadResult.data.info().selectedTrack()
        );


        callback.complete(Search.wasPlaylist(
                Search.SearchResult.PLAYLIST(),
                searchQuery.getSource().getSourceName(),
                searchQuery.getQuery(),
                playlistData
        ));
    }


    public void noMatches() {
        callback.complete(Search.wasNotFound(
                Search.SearchResult.NOT_FOUND(),
                searchQuery.getSource().getSourceName(),
                searchQuery.getQuery()
        ));
    }

    public void loadFailed(ClientException e) {
        callback.complete(Search.wasError(
                Search.SearchResult.ERROR(e.getMessage()),
                searchQuery.getSource().getSourceName(),
                searchQuery.getQuery()
        ));
    }

    private void trackSearch(ClientTrack[] searchLoadResult) {
        ArrayList<MusicAudioTrack> trackList = new ArrayList<>();
        for (ClientTrack track : searchLoadResult) {
            trackList.add(MusicAudioTrack.fromTrack( track, searchQuery.getRequestorData()));
        }

        callback.complete(Search.wasTrack(
                Search.SearchResult.SUCCESS(), searchQuery.getSource().getSourceName(),
                searchQuery.getQuery(),
                trackList
        ));
    }


    @Override
    public void accept(RestLoadResult<?> restLoadResult) {
        try {
            switch (restLoadResult.loadType) {
                case EMPTY -> noMatches();
                case ERROR -> loadFailed((ClientException) restLoadResult.data);
                case PLAYLIST -> playlistLoaded((PlaylistLoadResult) restLoadResult.data);
                case SEARCH -> trackSearch((ClientTrack[]) restLoadResult.data);
                case TRACK -> trackLoaded((ClientTrack) restLoadResult.data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}