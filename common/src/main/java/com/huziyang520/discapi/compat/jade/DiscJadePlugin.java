package com.huziyang520.discapi.compat.jade;

import com.huziyang520.discapi.Constants;
import net.minecraft.world.level.block.JukeboxBlock;
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
        registration.registerBlockComponent(JukeboxProgressProvider.INSTANCE, JukeboxBlock.class);
        Constants.LOG.info("DiscAPI hooked into Jade");
    }
}
