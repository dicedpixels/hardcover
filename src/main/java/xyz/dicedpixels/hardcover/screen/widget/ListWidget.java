package xyz.dicedpixels.hardcover.screen.widget;

import net.minecraft.client.MinecraftClient;

import xyz.dicedpixels.hardcover.screen.entry.ConfigurationEntry;
import xyz.dicedpixels.pixel.client.screen.entry.AbstractEntry;
import xyz.dicedpixels.pixel.client.screen.widget.AbstractListWidget;

public class ListWidget extends AbstractListWidget<AbstractEntry> {
    public ListWidget(MinecraftClient client, int width, int height, int y, int itemHeight) {
        super(client, width, height, y, itemHeight);
    }

    public void addEntry(ConfigurationEntry entry) {
        super.addEntry(entry);
    }

    public void reset() {
        clearEntries();
        setScrollAmount(0);
    }

    @Override
    public int getRowWidth() {
        return width;
    }

    @Override
    protected int getScrollbarPositionX() {
        return super.getScrollbarPositionX() + 45;
    }
}
