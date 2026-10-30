package com.huziyang520.discapi.compat.jade;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.JukeboxSongPlayer;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IServerDataProvider;

/** Server half: the client block entity never receives the elapsed ticks, so send a snapshot. */
public enum JukeboxDataProvider implements IServerDataProvider<BlockAccessor> {

    INSTANCE;

    static final String SONG = "discapi_song";
    static final String TOTAL = "discapi_song_ticks";
    static final String ELAPSED = "discapi_song_elapsed";

    private static final Identifier UID = Identifier.fromNamespaceAndPath("discapi", "jukebox_song_data");

    @Override
    public void appendServerData(CompoundTag tag, BlockAccessor accessor) {
        if (!(accessor.getBlockEntity() instanceof JukeboxBlockEntity jukebox)) {
            return;
        }
        JukeboxSongPlayer player = jukebox.getSongPlayer();
        JukeboxSong song = player.getSong();
        if (song == null || !player.isPlaying()) {
            return;
        }
        tag.putString(SONG, JukeboxProgressProvider.songId(jukebox, song).toString());
        tag.putInt(TOTAL, song.lengthInTicks());
        tag.putInt(ELAPSED, (int) player.getTicksSinceSongStarted());
    }

    @Override
    public Identifier getUid() {
        return UID;
    }
}
