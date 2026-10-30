package com.huziyang520.discapi.compat.jade;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.JukeboxPlayable;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.JukeboxSongPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

/** Client half: draws the progress bar plus elapsed and total time, interpolated between server ticks. */
public enum JukeboxProgressProvider implements IBlockComponentProvider {

    INSTANCE;

    private static final Identifier UID = Identifier.fromNamespaceAndPath("discapi", "jukebox_progress");
    private static final int BARS = 20;
    private static final long TICK_MILLIS = 50L;

    private static String clockSong = "";
    private static int clockTicks;
    private static long clockAt;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        JukeboxBlockEntity jukebox = jukebox(accessor);
        JukeboxSong song = null;
        boolean playing = false;
        int total = 0;
        int base = -1;

        if (jukebox != null) {
            JukeboxSongPlayer player = jukebox.getSongPlayer();
            playing = player.isPlaying();
            song = player.getSong();
            if (song == null) {
                song = itemSong(jukebox);
            } else {
                base = (int) player.getTicksSinceSongStarted();
            }
            if (song != null) {
                total = song.lengthInTicks();
            }
        }

        CompoundTag data = accessor.getServerData();
        String songKey = "";
        if (data != null && data.contains(JukeboxDataProvider.TOTAL)) {
            total = Math.max(total, data.getIntOr(JukeboxDataProvider.TOTAL, 0));
            songKey = data.getStringOr(JukeboxDataProvider.SONG, "");
            base = data.getIntOr(JukeboxDataProvider.ELAPSED, base);
            playing = true;
        } else if (song != null) {
            songKey = songId(jukebox, song).toString();
        }
        if (total <= 0 || !playing) {
            return;
        }

        int elapsed = elapsed(songKey, total, base);
        tooltip.add(Component.literal(bar(elapsed, total)).withStyle(ChatFormatting.AQUA));
        tooltip.add(Component.translatable("jade.discapi.progress", clock(elapsed), clock(total))
                .withStyle(ChatFormatting.GRAY));
    }

    @Override
    public Identifier getUid() {
        return UID;
    }

    static Identifier songId(JukeboxBlockEntity jukebox, JukeboxSong song) {
        ItemStack stack = jukebox.getTheItem();
        JukeboxPlayable playable = stack.get(DataComponents.JUKEBOX_PLAYABLE);
        if (playable != null) {
            Identifier key = playable.song().unwrapKey().map(ref -> ref.identifier()).orElse(null);
            if (key != null) {
                return key;
            }
        }
        return Identifier.fromNamespaceAndPath("discapi", Integer.toHexString(song.hashCode()));
    }

    private static JukeboxSong itemSong(JukeboxBlockEntity jukebox) {
        JukeboxPlayable playable = jukebox.getTheItem().get(DataComponents.JUKEBOX_PLAYABLE);
        return playable == null ? null : playable.song().value();
    }

    private static JukeboxBlockEntity jukebox(BlockAccessor accessor) {
        BlockEntity entity = accessor.getBlockEntity();
        if (entity == null && accessor.getLevel() != null) {
            entity = accessor.getLevel().getBlockEntity(accessor.getPosition());
        }
        return entity instanceof JukeboxBlockEntity jukebox ? jukebox : null;
    }

    /** The server refreshes at its own pace, so keep a local clock and restart it on every new snapshot. */
    private static int elapsed(String song, int total, int base) {
        long now = System.currentTimeMillis();
        if (base < 0) {
            base = 0;
        }
        if (!song.equals(clockSong) || base != clockTicks) {
            clockSong = song;
            clockTicks = base;
            clockAt = now;
        }
        int ticks = clockTicks + (int) ((now - clockAt) / TICK_MILLIS);
        return Math.max(0, Math.min(total, ticks));
    }

    private static String bar(int elapsed, int total) {
        int filled = Math.max(0, Math.min(BARS, (int) ((long) elapsed * BARS / total)));
        StringBuilder sb = new StringBuilder(BARS);
        for (int i = 0; i < BARS; i++) {
            sb.append(i < filled ? '|' : ':');
        }
        return sb.toString();
    }

    private static String clock(int ticks) {
        int seconds = Math.max(0, ticks) / 20;
        return String.format("%d:%02d", seconds / 60, seconds % 60);
    }
}
