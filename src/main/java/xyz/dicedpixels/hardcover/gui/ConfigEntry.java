package xyz.dicedpixels.hardcover.gui;

import java.util.List;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.gui.widget.EmptyWidget;

final class ConfigEntry extends AbstractEntry {
    private final List<ClickableWidget> children = new ObjectArrayList<>();
    private final DirectionalLayoutWidget layout = DirectionalLayoutWidget.horizontal().spacing(5);

    public ConfigEntry(ClickableWidget... widgets) {
        for (var widget : widgets) {
            children.add(widget);
            layout.add(widget);

            if (widgets.length == 1) {
                layout.add(EmptyWidget.ofWidth(160));
            }
        }

        layout.refreshPositions();
    }

    public static ConfigEntry create(ClickableWidget left, ClickableWidget right) {
        left.setDimensions(160, 20);
        right.setDimensions(160, 20);
        return new ConfigEntry(left, right);
    }

    public static ConfigEntry create(ClickableWidget widget) {
        widget.setDimensions(160, 20);
        return new ConfigEntry(widget);
    }

    @Override
    public List<? extends Element> children() {
        return children;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, boolean hovered, float deltaTicks) {
        layout.setPosition((getWidth() / 2) - (layout.getWidth() / 2), getY());
        layout.forEachChild(child -> child.render(context, mouseX, mouseY, deltaTicks));
    }

    @Override
    public List<? extends Selectable> selectableChildren() {
        return children;
    }
}
