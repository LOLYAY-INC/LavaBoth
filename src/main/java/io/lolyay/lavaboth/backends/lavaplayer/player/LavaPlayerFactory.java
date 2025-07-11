package io.lolyay.lavaboth.backends.lavaplayer.player;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import io.lolyay.lavaboth.backends.common.AbstractPlayer;
import io.lolyay.lavaboth.backends.common.AbstractPlayerFactory;
import io.lolyay.lavaboth.backends.lavaplayer.player.LavaPlayerPlayerManager;
import io.lolyay.lavaboth.backends.lavaplayer.LavaPlayerEventTranslator;

import java.util.HashMap;
import java.util.Map;

public class LavaPlayerFactory extends AbstractPlayerFactory {
    private final Map<Long, LavaPlayerPlayer> players = new HashMap<>();
    private final LavaPlayerPlayerManager audioPlayerManager;

    public LavaPlayerFactory(LavaPlayerPlayerManager audioPlayerManager) {
        this.audioPlayerManager = audioPlayerManager;
    }

    @Override
    public LavaPlayerPlayer getOrCreatePlayer(long guildId) {
        if(!players.containsKey(guildId)) {
            LavaPlayerPlayer player = createPlayer(guildId);
            players.put(guildId, player);
            return player;
        } else {
            return players.get(guildId);
        }
    }

    @Override
    protected LavaPlayerPlayer createPlayer(long guildId) {
        AudioPlayer player = audioPlayerManager.getAudioPlayerManager().createPlayer();
        LavaPlayerPlayer playerPlayer = new LavaPlayerPlayer(player, audioPlayerManager, guildId);
        player.addListener(new LavaPlayerEventTranslator(audioPlayerManager,playerPlayer));
        return playerPlayer;
    }

    @Override
    public void removePlayer(long guildId) {
        players.remove(guildId);

    }
}
