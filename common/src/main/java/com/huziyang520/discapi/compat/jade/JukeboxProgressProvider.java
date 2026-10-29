package com.huziyang520.discapi.compat.jade;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.JukeboxSongPlayer;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

/** Reads the song clock from the client block entity, so no extra packet is needed. */
public enum JukeboxProgressProvider implements IBlockComponentProvider {

    INSTANCE;

    private static final Identifier UID = Identifier.fromNamespaceAndPath("discapi", "jukebox_progress");
    private static final int BARS = 20;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if (!(accessor.getBlockEntity() instanceof JukeboxBlockEntity jukebox)) {
            return;
        }
        JukeboxSongPlayer player = jukebox.getSongPlayer();
        JukeboxSong song = player.getSong();
        if (song == null) {
            return;
        }
        int total = Math.max(1, song.lengthInTicks());
        int elapsed = (int) Math.min(total, player.getTicksSinceSongStarted());
        tooltip.add(Component.literal(bar(elapsed, total)));
        tooltip.add(Component.translatable("jade.discapi.progress", clock(elapsed), clock(total)));
    }

    @Override
    public Identifier getUid() {
        return UID;
    }

    private static String bar(int elapsed, int total) {
        int filled = elapsed * BARS / total;
        StringBuilder sb = new StringBuilder(BARS);
        for (int i = 0; i < BARS; i++) {
            sb.append(i < filled ? '#' : '-');
        }
        return sb.toString();
    }

    private static String clock(int ticks) {
        int seconds = ticks / 20;
        return String.format("%d:%02d", seconds / 60, seconds % 60);
    }
}
