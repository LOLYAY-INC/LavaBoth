package io.lolyay.lavaboth;

import io.lolyay.jlavalink.data.ConnectionInfo;
import io.lolyay.lavaboth.backends.common.AbstractPlayer;
import io.lolyay.lavaboth.backends.common.AbstractPlayerManager;
import io.lolyay.lavaboth.backends.lavalinkclient.player.LavaLinkPlayerManager;
import io.lolyay.lavaboth.search.Search;
import io.lolyay.lavaboth.tracks.MusicAudioTrack;
import io.lolyay.lavaboth.utils.Logger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

public class LavalinkNodeTest {
    private static final String discordToken = System.getenv("DISCORD.TOKEN");
    private static AbstractPlayerManager playerManager;
    private static JDA jda;

    @Test
    @DisplayName("Test lavalinkNode connection, playback and stop")
    public void lavalinkPlayerTest() {
        JDABuilder jdaBuilder = JDABuilder.createDefault(discordToken);
        playerManager = null;
        try {
            playerManager = new LavaLinkPlayerManager(jdaBuilder, "1389646794308456558",
                    new ConnectionInfo("CustomHQNode1", URI.create("ws://api.lolyay.dev:42691"), false, System.getenv("LAVA.PASSWORD")));
        } catch (Exception e) {
            fail("Error creating player manager",e);
        }
        try {
            jda = jdaBuilder.build().awaitReady();
        } catch (Exception e) {
            fail("Error creating JDA",e);
        }
        playerManager.getSearchManager().registerDefaultSearchers();
        searchResultHandler(playerManager.getSearchManager().search("never gonna give you up").join());

    }

    private void searchResultHandler(Search search){
        switch (search.result().getStatus()){
            case NOT_FOUND -> {
                fail("Track could not be found, " + search);
            }
            case ERROR -> {
                fail("Error searching track, " + search);
            }
            case SUCCESS -> {
                if (search.track().isEmpty()) {
                    fail("Track is empty, " + search);
                }
                Logger.log(search.track().get().toString());
                playTrack(search.track().get());
            }
            case PLAYLIST -> {
                fail("Track is a playlist, " + search);
            }
        }
    }

    private void playTrack(MusicAudioTrack track){
        AbstractPlayer player = playerManager.getPlayerFactory().getOrCreatePlayer(1382319800784519168L);
        player.connect(jda.getVoiceChannelById(1382319801237770343L));
        player.play(track);
        wait(3);
        player.setVolume(90);
        wait(1);
        player.pause();
        wait(1);
        player.resume();
        wait(1);
        player.stop();
        player.disconnect(jda.getGuildById(1382319800784519168L));
        jda.shutdown();
    }

    private void wait(int seconds){
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
           fail("Error waiting",e);
        }
    }
}
