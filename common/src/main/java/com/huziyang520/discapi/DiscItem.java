package com.huziyang520.discapi;

import com.huziyang520.keenlib.text.KeenText;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

/**
 * Shows the artist line: from the artist component, or from the item's own language key.
 */
public class DiscItem extends Item {

    public DiscItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display,
                                Consumer<Component> tooltip, TooltipFlag flag) {
        String artist = readArtist(stack);
        if (artist != null && !artist.isBlank()) {
            tooltip.accept(KeenText.trans("tooltip.discapi.artist", "Artist: %s", artist)
                    .withStyle(ChatFormatting.GRAY));
            return;
        }
        String key = getDescriptionId() + ".artist";
        Component line = Component.translatable(key);
        if (!line.getString().equals(key)) {
            tooltip.accept(line.copy().withStyle(ChatFormatting.GRAY));
        }
    }

    private static String readArtist(ItemStack stack) {
        try {
            var type = DiscApi.artist();
            return type == null ? null : stack.get(type);
        } catch (RuntimeException ignored) {
            return null;
        }
    }
}
