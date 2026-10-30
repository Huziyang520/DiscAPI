package com.huziyang520.discapi.compat.jade;

import com.huziyang520.discapi.DiscApi;
import net.minecraft.resources.Identifier;
import snownee.jade.api.config.ConfigIcon;

/** Jade 26.2+ can show an icon in the plugin list; the disc item is the most telling one. */
public final class IconProgressProvider extends JukeboxProgressProvider {

    public static final IconProgressProvider INSTANCE = new IconProgressProvider();

    private IconProgressProvider() {
    }

    @Override
    public Identifier getUid() {
        return super.getUid();
    }

    @Override
    public ConfigIcon getConfigIcon() {
        return ConfigIcon.item(DiscApi.disc().value());
    }
}
