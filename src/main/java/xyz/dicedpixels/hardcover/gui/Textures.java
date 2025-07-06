package xyz.dicedpixels.hardcover.gui;

import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.util.Identifier;

import xyz.dicedpixels.hardcover.Hardcover;

public final class Textures {
    public static final SelectableButtonTextures CRAFTING_FILTER = SelectableButtonTextures.of("crafting_filter");
    public static final Identifier CRAFTING_GRID = textureID("crafting_grid");
    public static final SelectableTexture CRAFTING_OVERLAY = SelectableTexture.of("crafting_overlay");
    public static final SelectableTexture CRAFTING_OVERLAY_DISABLED = SelectableTexture.of("crafting_overlay_disabled");
    public static final SelectableButtonTextures FURNACE_FILTER = SelectableButtonTextures.of("furnace_filter");
    public static final SelectableTexture PAGE_BACKWARD = SelectableTexture.of("page_backward");
    public static final SelectableTexture PAGE_FORWARD = SelectableTexture.of("page_forward");
    public static final SelectableTexture TABS_DOWN = SelectableTexture.of("tab_down");
    public static final SelectableTexture TABS_DOWN_COMPACT = SelectableTexture.of("tab_down_compact");
    public static final SelectableTexture TABS_UP = SelectableTexture.of("tab_up");
    public static final SelectableTexture TABS_UP_COMPACT = SelectableTexture.of("tab_up_compact");
    public static final SelectableTexture TAB_COMPACT = SelectableTexture.of("tab_compact");

    public static Identifier textureID(String key) {
        return Identifier.of(Hardcover.MOD_ID, "recipe_book/" + key);
    }

    public static class SelectableButtonTextures {
        private final Identifier texture;
        private final Identifier textureEnabled;
        private final Identifier textureSelected;
        private final Identifier textureSelectedEnabled;

        private SelectableButtonTextures(String key) {
            this.texture = textureID(key);
            this.textureEnabled = textureID(key + "_enabled");
            this.textureSelected = textureID(key + "_selected");
            this.textureSelectedEnabled = textureID(key + "_enabled_selected");
        }

        public static SelectableButtonTextures of(String key) {
            return new SelectableButtonTextures(key);
        }

        public ButtonTextures asButtonTextures() {
            return new ButtonTextures(textureEnabled, texture, textureSelectedEnabled, textureSelected);
        }
    }

    public static class SelectableTexture {
        private final Identifier selectedTexture;
        private final Identifier texture;

        private SelectableTexture(String key) {
            selectedTexture = textureID(key + "_selected");
            texture = textureID(key);
        }

        public static SelectableTexture of(String key) {
            return new SelectableTexture(key);
        }

        public ButtonTextures asButtonTextures() {
            return new ButtonTextures(texture, selectedTexture);
        }

        public Identifier getTexture(boolean isSelected) {
            return isSelected ? selectedTexture : texture;
        }
    }
}
