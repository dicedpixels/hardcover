package xyz.dicedpixels.hardcover.gui;

import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import xyz.dicedpixels.hardcover.config.ConfigCategory;

final class CategoryEntry extends AbstractEntry {
    private final DirectionalLayoutWidget layout = DirectionalLayoutWidget.horizontal();

    private CategoryEntry(Text name) {
        var widget = new TextWidget(name.copy().styled(style -> style.withFormatting(Formatting.GRAY)), MinecraftClient.getInstance().textRenderer);

        widget.setHeight(20);
        layout.getMainPositioner().marginTop(1);
        layout.add(widget);
        layout.refreshPositions();
    }

    public static CategoryEntry create(ConfigCategory category) {
        return new CategoryEntry(category.getName());
    }

    @Override
    public List<? extends Element> children() {
        return List.of();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, boolean hovered, float deltaTicks) {
        layout.setPosition((getWidth() / 2) - (layout.getWidth() / 2), getY());
        layout.forEachChild(child -> child.render(context, mouseX, mouseY, deltaTicks));
    }

    @Override
    public List<? extends Selectable> selectableChildren() {
        return List.of();
    }
}
