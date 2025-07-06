package xyz.dicedpixels.hardcover.gui;

import java.util.List;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.recipebook.RecipeAlternativesWidget.AlternativeButtonWidget.InputSlot;
import net.minecraft.client.gui.tooltip.TooltipComponent;

public final class RecipeTooltipComponent implements TooltipComponent {
    private final int index;
    private final List<InputSlot> slots;

    private RecipeTooltipComponent(List<InputSlot> slots, int index) {
        this.slots = slots;
        this.index = index;
    }

    public static List<TooltipComponent> asList(List<InputSlot> slots, int index) {
        return List.of(new RecipeTooltipComponent(slots, index));
    }

    @Override
    public void drawItems(TextRenderer renderer, int x, int y, int width, int height, DrawContext context) {
        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, Textures.CRAFTING_GRID, x, y, 56, 56, 0xFFFFFFFF);

        for (var indexY = 0; indexY < 3; ++indexY) {
            for (var indexX = 0; indexX < 3; ++indexX) {
                drawSlot(x + indexX * 18, y + indexY * 18, indexX, indexY, context);
            }
        }
    }

    private void drawSlot(int posX, int posY, int indexX, int indexY, DrawContext context) {
        for (var slot : slots) {
            if (!slot.stacks().isEmpty() && slot.x() % 3 == indexY && slot.y() % 3 == indexX) {
                context.drawItem(slot.get(index), posX + 2, posY + 2);
            }
        }
    }

    @Override
    public int getHeight(TextRenderer renderer) {
        return 58;
    }

    @Override
    public int getWidth(TextRenderer renderer) {
        return 56;
    }
}
