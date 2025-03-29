package xyz.dicedpixels.hardcover.gui;

import java.util.List;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.gui.widget.ElementListWidget.Entry;
import net.minecraft.client.gui.widget.EmptyWidget;

public final class ConfigEntry extends Entry<ConfigEntry> {
    private static final int DEFAULT_WIDTH = 160;
    private final List<ClickableWidget> childWidgets = new ObjectArrayList<>();
    private final DirectionalLayoutWidget layout = DirectionalLayoutWidget.horizontal().spacing(5);

    public ConfigEntry(ClickableWidget... widgets) {
        for (ClickableWidget widget : widgets) {
            childWidgets.add(widget);
            layout.add(widget);

            if (widgets.length == 1) {
                layout.add(EmptyWidget.ofWidth(DEFAULT_WIDTH));
            }
        }

        layout.refreshPositions();
    }

    public static ConfigEntry create(ClickableWidget left, ClickableWidget right) {
        left.setDimensions(DEFAULT_WIDTH, 20);
        right.setDimensions(DEFAULT_WIDTH, 20);
        return new ConfigEntry(left, right);
    }

    public static ConfigEntry create(ClickableWidget widget) {
        widget.setDimensions(DEFAULT_WIDTH, 20);
        return new ConfigEntry(widget);
    }

    @Override
    public List<? extends Element> children() {
        return childWidgets;
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        layout.setPosition((entryWidth / 2) - (layout.getWidth() / 2), y);
        layout.forEachChild(child -> child.render(context, mouseX, mouseY, tickDelta));
    }

    @Override
    public List<? extends Selectable> selectableChildren() {
        return childWidgets;
    }
}
