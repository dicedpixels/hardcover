package xyz.dicedpixels.hardcover.screen.tooltip;

import java.util.List;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.recipebook.RecipeAlternativesWidget.AlternativeButtonWidget.InputSlot;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.util.math.MathHelper;

import xyz.dicedpixels.hardcover.util.Textures;

public class RecipeTooltipComponent implements TooltipComponent {
    private final List<InputSlot> inputSlots;
    private final float time;

    public RecipeTooltipComponent(List<InputSlot> inputSlots, float time) {
        this.inputSlots = inputSlots;
        this.time = time;
    }

    @Override
    public int getHeight() {
        return 58;
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        return 56;
    }

    @Override
    public void drawItems(TextRenderer textRenderer, int x, int y, DrawContext context) {
        context.drawGuiTexture(Textures.CRAFTING_GRID.get(), x, y, 0, 56, 56);
        for (int indexY = 0; indexY < 3; ++indexY) {
            for (int indexX = 0; indexX < 3; ++indexX) {
                int posX = x + indexX * 18;
                int posY = y + indexY * 18;
                drawSlot(posX, posY, indexX, indexY, context);
            }
        }
    }

    private void drawSlot(int posX, int posY, int indexX, int indexY, DrawContext context) {
        for (var slot : inputSlots) {
            if (slot.stacks.length > 0 && slot.x % 3 == indexY && slot.y % 3 == indexX) {
                context.drawItem(slot.stacks[MathHelper.floor(time / 30.0F) % slot.stacks.length], posX + 2, posY + 2);
            }
        }
    }
}
