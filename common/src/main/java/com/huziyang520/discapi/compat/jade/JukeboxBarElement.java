package com.huziyang520.discapi.compat.jade;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import snownee.jade.api.ui.Element;

/** One tooltip row: rounded track, filled part and a round knob, with the time on the right. */
final class JukeboxBarElement extends Element {

    private static final int BAR_WIDTH = 110;
    private static final int BAR_HEIGHT = 5;
    private static final int KNOB_RADIUS = 3;
    private static final int TRACK_COLOR = 0xFF3C3C3C;
    private static final int FILL_COLOR = 0xFFE6E6E6;
    private static final int KNOB_COLOR = 0xFFFFFFFF;
    private static final int GAP = 6;

    private final float progress;
    private final Component text;

    JukeboxBarElement(int elapsed, int total) {
        this.progress = total <= 0 ? 0F : Math.max(0F, Math.min(1F, elapsed / (float) total));
        this.text = Component.translatable("jade.discapi.time", clock(elapsed), clock(total))
                .withStyle(ChatFormatting.GRAY);
        int textWidth = Minecraft.getInstance().font.width(text);
        this.width = BAR_WIDTH + GAP + textWidth;
        this.height = Math.max(BAR_HEIGHT, 9);
    }

    @Override
    public Component getNarration() {
        return text;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        int x = getX();
        int y = getY() + (height - BAR_HEIGHT) / 2;

        pill(graphics, x, y, BAR_WIDTH, TRACK_COLOR);
        int filled = Math.round(BAR_WIDTH * progress);
        if (filled > 0) {
            int width = Math.min(filled, BAR_WIDTH);
            graphics.fill(x + 2, y, x + width, y + 5, FILL_COLOR);
            graphics.fill(x + 1, y + 1, x + width, y + 4, FILL_COLOR);
            graphics.fill(x, y + 2, x + width, y + 3, FILL_COLOR);
            knob(graphics, x + width, y + 2);
        }

        graphics.text(Minecraft.getInstance().font, text, x + BAR_WIDTH + GAP, getY() + 1, 0xFFFFFFFF);
    }

    private static void pill(GuiGraphicsExtractor graphics, int x, int y, int width, int color) {
        graphics.fill(x + 2, y, x + width - 2, y + BAR_HEIGHT, color);
        graphics.fill(x + 1, y + 1, x + width - 1, y + BAR_HEIGHT - 1, color);
        graphics.fill(x, y + 2, x + width, y + BAR_HEIGHT - 2, color);
    }

    private static void knob(GuiGraphicsExtractor graphics, int cx, int cy) {
        for (int dy = -KNOB_RADIUS; dy <= KNOB_RADIUS; dy++) {
            int half = (int) Math.round(Math.sqrt(KNOB_RADIUS * KNOB_RADIUS - dy * dy));
            graphics.fill(cx - half, cy + dy, cx + half + 1, cy + dy + 1, KNOB_COLOR);
        }
    }

    private static String clock(int ticks) {
        int seconds = Math.max(0, ticks) / 20;
        return String.format("%d:%02d", seconds / 60, seconds % 60);
    }
}
