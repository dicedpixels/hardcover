package xyz.dicedpixels.hardcover.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;

import xyz.dicedpixels.hardcover.config.ConfigCategory;

final class ConfigListWidget extends ElementListWidget<AbstractEntry> {
    public ConfigListWidget(MinecraftClient client, int width, int height, int y, int itemHeight) {
        super(client, width, height, y, itemHeight);
    }

    public void createAndAddEntry(ConfigCategory category) {
        addEntry(CategoryEntry.create(category));
    }

    public void createAndAddEntry(ClickableWidget left, ClickableWidget right) {
        addEntry(ConfigEntry.create(left, right));
    }

    public void createAndAddEntry(ClickableWidget widget) {
        addEntry(ConfigEntry.create(widget));
    }

    @Override
    public int getRowWidth() {
        return width;
    }

    @Override
    protected int getScrollbarX() {
        return width - 46;
    }
}
