package io.lolyay.lavaboth.backends.lavalinkclient.player;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import io.lolyay.jlavalink.v4.datatypes.ClientPlayer;
import io.lolyay.lavaboth.backends.common.AbstractPlayerFactory;
import io.lolyay.lavaboth.backends.lavaplayer.LavaPlayerEventTranslator;
import io.lolyay.lavaboth.backends.lavaplayer.player.LavaPlayerPlayer;
import io.lolyay.lavaboth.backends.lavaplayer.player.LavaPlayerPlayerManager;

import java.util.HashMap;
import java.util.Map;

public class LinkPlayerFactory extends AbstractPlayerFactory {
    private final Map<Long, LavaLinkPlayer> players = new HashMap<>();
    private final LavaLinkPlayerManager audioPlayerManager;

    public LinkPlayerFactory(LavaLinkPlayerManager audioPlayerManager) {
        this.audioPlayerManager = audioPlayerManager;
    }

    @Override
    public LavaLinkPlayer getOrCreatePlayer(long guildId) {
        if(!players.containsKey(guildId)) {
            LavaLinkPlayer player = createPlayer(guildId);
            players.put(guildId, player);
            return player;
        } else {
            return players.get(guildId);
        }
    }

    @Override
    protected LavaLinkPlayer createPlayer(long guildId) {
        ClientPlayer player = audioPlayerManager.getJLavalinkClient().getClientPlayerFactory().getOrCreatePlayer(String.valueOf(guildId));
        return new LavaLinkPlayer(player, audioPlayerManager, audioPlayerManager.getJLavalinkClient());

    }

    @Override
    public void removePlayer(long guildId) {
        players.remove(guildId);

    }
}
