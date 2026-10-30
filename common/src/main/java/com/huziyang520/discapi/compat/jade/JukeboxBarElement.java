package com.huziyang520.discapi.compat.jade;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import snownee.jade.api.ui.Element;

/** One tooltip row: rounded track, filled part and a round knob, with the time on the right. */
final class JukeboxBarElement extends Element {

    private static final int BAR_WIDTH = 110;
    private static final int BAR_HEIGHT = 6;
    private static final int[] BAR_INSETS = {3, 1, 0, 0, 1, 3};
    private static final int KNOB_SIZE = 10;
    private static final int[] KNOB_MASK = {
            0b0001111000,
            0b0111111110,
            0b0111111110,
            0b1111111111,
            0b1111111111,
            0b1111111111,
            0b1111111111,
            0b0111111110,
            0b0111111110,
            0b0001111000
    };
    private static final int TRACK_COLOR = 0xFF2E2E2E;
    private static final int FILL_COLOR = 0xFF6FC7F5;
    private static final int KNOB_COLOR = 0xFFFFFFFF;
    private static final int GAP = 6;

    private final float progress;
    private final Component text;

    JukeboxBarElement(int elapsed, int total) {
        this.progress = total <= 0 ? 0F : Math.max(0F, Math.min(1F, elapsed / (float) total));
        this.text = Component.translatable("jade.discapi.time", clock(elapsed), clock(total))
                .withStyle(ChatFormatting.GRAY);
        this.width = BAR_WIDTH + GAP + Minecraft.getInstance().font.width(text);
        this.height = KNOB_SIZE;
    }

    @Override
    public Component getNarration() {
        return text;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        int x = getX();
        int barY = getY() + (height - BAR_HEIGHT) / 2;
        pill(graphics, x, barY, BAR_WIDTH, TRACK_COLOR);

        int knobCenter = Math.round(BAR_WIDTH * progress);
        int filled = Math.max(0, knobCenter - KNOB_SIZE / 2);
        if (filled > 0) {
            fillBar(graphics, x, barY, filled);
        }
        knob(graphics, x + knobCenter - KNOB_SIZE / 2, getY());

        graphics.text(Minecraft.getInstance().font, text, x + BAR_WIDTH + GAP, getY() + 1, 0xFFFFFFFF);
    }

    private static void pill(GuiGraphicsExtractor graphics, int x, int y, int width, int color) {
        for (int row = 0; row < BAR_HEIGHT; row++) {
            int inset = BAR_INSETS[row];
            graphics.fill(x + inset, y + row, x + width - inset, y + row + 1, color);
        }
    }

    private static void fillBar(GuiGraphicsExtractor graphics, int x, int y, int width) {
        for (int row = 0; row < BAR_HEIGHT; row++) {
            int inset = BAR_INSETS[row];
            int from = x + inset;
            if (from >= x + width) {
                continue;
            }
            graphics.fill(from, y + row, x + width, y + row + 1, FILL_COLOR);
        }
    }

    private static void knob(GuiGraphicsExtractor graphics, int x, int y) {
        for (int row = 0; row < KNOB_SIZE; row++) {
            int bits = KNOB_MASK[row];
            int col = 0;
            while (col < KNOB_SIZE) {
                if ((bits & (1 << (KNOB_SIZE - 1 - col))) == 0) {
                    col++;
                    continue;
                }
                int run = col;
                while (run < KNOB_SIZE && (bits & (1 << (KNOB_SIZE - 1 - run))) != 0) {
                    run++;
                }
                graphics.fill(x + col, y + row, x + run, y + row + 1, KNOB_COLOR);
                col = run;
            }
        }
    }

    private static String clock(int ticks) {
        int seconds = Math.max(0, ticks) / 20;
        return String.format("%d:%02d", seconds / 60, seconds % 60);
    }
}
