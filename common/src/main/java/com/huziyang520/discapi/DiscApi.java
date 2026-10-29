package com.huziyang520.discapi;

import com.huziyang520.keenlib.registry.KeenRegistry;
import com.huziyang520.keenlib.text.KeenText;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.JukeboxSong;

import java.util.function.Function;

/**
 * Registers the generic disc item, its artist component and the creative tab.
 */
public final class DiscApi {

    private static final KeenRegistry REGISTRY = KeenRegistry.of(Constants.MOD_ID);

    private static Holder<Item> disc;
    private static Holder<DataComponentType<String>> artist;
    private static boolean initialized;

    private DiscApi() {
    }

    public static void bind(Object eventBus) {
        REGISTRY.bind(eventBus);
    }

    public static void init() {
        if (initialized) {
            return;
        }
        initialized = true;

        artist = REGISTRY.component("artist", Codec.STRING, ByteBufCodecs.STRING_UTF8);
        disc = REGISTRY.item("disc", DiscItem::new);
        REGISTRY.tab("main",
                KeenText.trans("itemGroup.discapi.main", "DiscAPI"),
                () -> new ItemStack(disc.value()),
                output -> output.accept(new ItemStack(disc.value())));

        Constants.LOG.info("DiscAPI registered the generic disc item");
    }

    /**
     * The generic disc item; songs and colors are carried by item components.
     */
    public static Holder<Item> disc() {
        return disc;
    }

    public static DataComponentType<String> artist() {
        return artist == null ? null : artist.value();
    }

    /**
     * Builds a disc item bound to one jukebox song, for mods that ship their own discs.
     */
    public static Function<Item.Properties, DiscItem> discFactory(ResourceKey<JukeboxSong> song) {
        return properties -> new DiscItem(properties.stacksTo(1).jukeboxPlayable(song));
    }

    public static ResourceKey<JukeboxSong> songKey(String namespace, String path) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, Identifier.fromNamespaceAndPath(namespace, path));
    }
}
