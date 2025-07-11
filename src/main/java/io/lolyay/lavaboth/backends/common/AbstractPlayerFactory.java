package io.lolyay.lavaboth.backends.common;

import io.lolyay.lavaboth.backends.lavaplayer.player.LavaPlayerPlayer;
import reactor.util.annotation.Nullable;

public abstract class AbstractPlayerFactory {


    public abstract AbstractPlayer getOrCreatePlayer(@Nullable long guildId);

    protected abstract AbstractPlayer createPlayer(@Nullable long guildId);

    public abstract void removePlayer(long guildId);
}
