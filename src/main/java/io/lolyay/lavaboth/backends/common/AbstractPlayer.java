package io.lolyay.lavaboth.backends.common;

import io.lolyay.lavaboth.tracks.MusicAudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;

public abstract class AbstractPlayer {
    private long guildId;

    public AbstractPlayer(long guildId) {
        this.guildId = guildId;
    }

    public AbstractPlayer() {
        this.guildId = 0;
    }

    public abstract void play(MusicAudioTrack track);

    public abstract void stop();

    public abstract void pause();
    public abstract void resume();

    public abstract void setVolume(int volume);

    public abstract MusicAudioTrack getCurrentTrack();

    public long getGuildId() {
        return guildId;
    }

    public abstract boolean isPaused();
    public abstract int getVolume();


    public abstract void connect(AudioChannel channel);
    public abstract void disconnect(Guild voiceChannel);

}
