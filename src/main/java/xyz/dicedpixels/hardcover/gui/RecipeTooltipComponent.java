package xyz.dicedpixels.hardcover.gui;

import java.util.List;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.recipebook.RecipeAlternativesWidget.AlternativeButtonWidget.InputSlot;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.render.RenderLayer;

public final class RecipeTooltipComponent implements TooltipComponent {
    private static final int TOOLTIP_SIZE = 56;
    private final List<InputSlot> inputSlots;
    private final int slotIndex;

    private RecipeTooltipComponent(List<InputSlot> inputSlots, int slotIndex) {
        this.inputSlots = inputSlots;
        this.slotIndex = slotIndex;
    }

    public static List<TooltipComponent> asList(List<InputSlot> inputSlots, int slotIndex) {
        return List.of(new RecipeTooltipComponent(inputSlots, slotIndex));
    }

    @Override
    public void drawItems(TextRenderer textRenderer, int x, int y, int width, int height, DrawContext context) {
        context.drawGuiTexture(RenderLayer::getGuiTextured, Textures.CRAFTING_GRID, x, y, TOOLTIP_SIZE, TOOLTIP_SIZE, 0xffffffff);

        for (var indexY = 0; indexY < 3; ++indexY) {
            for (int indexX = 0; indexX < 3; ++indexX) {
                var posX = x + indexX * 18;
                var posY = y + indexY * 18;

                drawSlot(posX, posY, indexX, indexY, context);
            }
        }
    }

    private void drawSlot(int posX, int posY, int indexX, int indexY, DrawContext context) {
        for (var slot : inputSlots) {
            if (!slot.stacks().isEmpty() && slot.x() % 3 == indexY && slot.y() % 3 == indexX) {
                context.drawItem(slot.get(slotIndex), posX + 2, posY + 2);
            }
        }
    }

    @Override
    public int getHeight(TextRenderer textRenderer) {
        return TOOLTIP_SIZE + 2;
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        return TOOLTIP_SIZE;
    }
}
