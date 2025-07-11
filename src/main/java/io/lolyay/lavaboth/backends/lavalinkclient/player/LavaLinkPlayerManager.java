package io.lolyay.lavaboth.backends.lavalinkclient.player;

import com.sedmelluq.discord.lavaplayer.format.AudioDataFormat;
import com.sedmelluq.discord.lavaplayer.player.AudioConfiguration;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import io.lolyay.jlavalink.JLavaLinkClient;
import io.lolyay.jlavalink.data.ConnectionInfo;
import io.lolyay.jlavalink.data.JLavaLinkClientInfo;
import io.lolyay.lavaboth.backends.common.AbstractPlayerManager;
import io.lolyay.lavaboth.backends.lavalinkclient.LinkVoiceDispatchInterceptor;
import io.lolyay.lavaboth.backends.lavalinkclient.search.LavaLinkSearchManager;
import io.lolyay.lavaboth.backends.lavaplayer.player.LavaPlayerFactory;
import io.lolyay.lavaboth.utils.Logger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class LavaLinkPlayerManager extends AbstractPlayerManager {
    private final JLavaLinkClient lavaLinkClient;
    private final LinkPlayerFactory playerFactory;
    private final LavaLinkSearchManager searchManager;


    public LavaLinkPlayerManager(JDABuilder jdaBuilder,
                                 String botUserId,
                                 ConnectionInfo connectionInfo) {
        JLavaLinkClientInfo clientInfo = new JLavaLinkClientInfo(
                "0.0.1",
                Long.parseLong(botUserId),
                "LavaBoth/Lolyay",
                connectionInfo,
                true,
                50
        );


        this.lavaLinkClient = new JLavaLinkClient(clientInfo,this);
        lavaLinkClient.init();

        playerFactory = new LinkPlayerFactory(this);
        searchManager = new LavaLinkSearchManager(this);

        jdaBuilder.setVoiceDispatchInterceptor(new LinkVoiceDispatchInterceptor(lavaLinkClient));

        Logger.log("LavaLinkPlayerManager created");
    }

    @Override
    public LinkPlayerFactory getPlayerFactory() {
        return playerFactory;
    }

    @Override
    public LavaLinkSearchManager getSearchManager() {
        return searchManager;
    }

    public JLavaLinkClient getJLavalinkClient() {
        return lavaLinkClient;
    }

}
