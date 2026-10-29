package com.huziyang520.discapi.fabric;

import com.huziyang520.discapi.DiscApi;
import net.fabricmc.api.ModInitializer;

/**
 * Fabric entry: binds the registry helper and registers content on both sides.
 */
public class DiscApiFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        DiscApi.bind(null);
        DiscApi.init();
    }
}
