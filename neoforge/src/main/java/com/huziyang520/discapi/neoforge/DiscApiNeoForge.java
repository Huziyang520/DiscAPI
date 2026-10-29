package com.huziyang520.discapi.neoforge;

import com.huziyang520.discapi.Constants;
import com.huziyang520.discapi.DiscApi;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

/**
 * NeoForge entry: registration is deferred, so the mod event bus is required.
 */
@Mod(Constants.MOD_ID)
public class DiscApiNeoForge {

    public DiscApiNeoForge(IEventBus eventBus, ModContainer container) {
        DiscApi.bind(eventBus);
        DiscApi.init();
    }
}
