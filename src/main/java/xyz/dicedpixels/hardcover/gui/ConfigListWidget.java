package xyz.dicedpixels.hardcover.gui;

import java.util.List;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ElementListWidget.Entry;

public class ConfigListWidget {
    public static class ConfigEntry extends Entry<ConfigEntry> {

        @Override
        public List<? extends Element> children() {
            return List.of();
        }

        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {}

        @Override
        public List<? extends Selectable> selectableChildren() {
            return List.of();
        }
    }
}
