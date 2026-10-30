package com.huziyang520.discapi.compat.jade;

import com.huziyang520.discapi.Constants;
import net.minecraft.world.level.block.JukeboxBlock;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

/** Optional Jade plugin: shows the progress of the song playing in a jukebox. */
@WailaPlugin("discapi")
public final class DiscJadePlugin implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(JukeboxDataProvider.INSTANCE, JukeboxBlock.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(progressProvider(), JukeboxBlock.class);
        Constants.LOG.info("DiscAPI hooked into Jade");
    }

    // Jade only gained config icons in 26.2+, so the icon variant lives in a per-target source set.
    private static IBlockComponentProvider progressProvider() {
        try {
            Class<?> type = Class.forName("com.huziyang520.discapi.compat.jade.IconProgressProvider");
            return (IBlockComponentProvider) type.getField("INSTANCE").get(null);
        } catch (Throwable ignored) {
            return JukeboxProgressProvider.INSTANCE;
        }
    }
}
