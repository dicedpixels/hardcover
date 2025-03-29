package xyz.dicedpixels.hardcover.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ElementListWidget;

public final class ConfigListWidget extends ElementListWidget<ConfigEntry> {
    public ConfigListWidget(MinecraftClient client, int width, int height, int y, int itemHeight) {
        super(client, width, height, y, itemHeight);
    }

    public void addConfigEntry(ConfigEntry entry) {
        addEntry(entry);
    }

    @Override
    public int getRowWidth() {
        return width;
    }
}
