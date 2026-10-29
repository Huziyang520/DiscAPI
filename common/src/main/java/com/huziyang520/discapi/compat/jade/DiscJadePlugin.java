package com.huziyang520.discapi.compat.jade;

import net.minecraft.world.level.block.JukeboxBlock;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

/** Optional Jade plugin: adds the playing progress of a jukebox to the tooltip. */
@WailaPlugin("discapi")
public final class DiscJadePlugin implements IWailaPlugin {

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(JukeboxProgressProvider.INSTANCE, JukeboxBlock.class);
    }
}
