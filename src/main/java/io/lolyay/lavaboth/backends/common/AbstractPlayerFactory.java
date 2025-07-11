package io.lolyay.lavaboth.backends.common;

import io.lolyay.lavaboth.backends.lavaplayer.player.LavaPlayerPlayer;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractPlayerFactory {


    public abstract AbstractPlayer getOrCreatePlayer(long guildId);

    protected abstract AbstractPlayer createPlayer(long guildId);

    public abstract void removePlayer(long guildId);
}
