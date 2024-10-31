package xyz.dicedpixels.hardcover.gui;

import java.util.List;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.gui.widget.ElementListWidget;

public class ConfigListWidget extends ElementListWidget<ConfigListWidget.ConfigEntry> {
    public ConfigListWidget(MinecraftClient client, int width, int height, int y, int itemHeight) {
        super(client, width, height, y, itemHeight);
    }

    public int addEntry(ConfigEntry entry) {
        return super.addEntry(entry);
    }

    @Override
    public int getRowWidth() {
        return width;
    }

    public void reset() {
        clearEntries();
        setScrollAmount(0);
    }

    public static class ConfigEntry extends Entry<ConfigEntry> {
        private final List<ClickableWidget> children = new ObjectArrayList<>();
        private final DirectionalLayoutWidget layout;

        private ConfigEntry(List<ClickableWidget> widgets) {
            layout = DirectionalLayoutWidget.horizontal().spacing(5);

            widgets.forEach(widget -> {
                children.add(widget);
                layout.add(widget);
            });

            layout.refreshPositions();
        }

        public static ConfigEntry of(ConfigScreen.OnOffCyclingButtonWidget left, ConfigScreen.OnOffCyclingButtonWidget right) {
            var leftCyclingButton = left.getButton();
            var rightCyclingButton = right.getButton();

            leftCyclingButton.setDimensions(ButtonWidget.DEFAULT_WIDTH, 20);
            rightCyclingButton.setDimensions(ButtonWidget.DEFAULT_WIDTH, 20);
            return new ConfigEntry(List.of(leftCyclingButton, rightCyclingButton));
        }

        public static ConfigEntry of(ConfigScreen.OnOffCyclingButtonWidget button) {
            var cyclingButton = button.getButton();
            cyclingButton.setDimensions((ButtonWidget.DEFAULT_WIDTH * 2) + 5, 20);
            return new ConfigEntry(List.of(cyclingButton));
        }

        @Override
        public List<? extends Element> children() {
            return children;
        }

        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            layout.setPosition((entryWidth / 2) - (layout.getWidth() / 2), y);
            layout.forEachChild(child -> child.render(context, mouseX, mouseY, tickDelta));
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return children;
        }
    }
}
