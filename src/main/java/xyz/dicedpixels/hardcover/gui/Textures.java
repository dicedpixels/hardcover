package xyz.dicedpixels.hardcover.gui;

import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.util.Identifier;

import xyz.dicedpixels.hardcover.Hardcover;

public final class Textures {
    public static final ButtonTextures CRAFTING_FILTER = new ButtonTextures(
        recipeBookTextureIdentifier("crafting_filter_enabled"),
        recipeBookTextureIdentifier("crafting_filter"),
        recipeBookTextureIdentifier("crafting_filter_enabled_selected"),
        recipeBookTextureIdentifier("crafting_filter_selected")
    );
    public static final Identifier CRAFTING_GRID = recipeBookTextureIdentifier("crafting_grid");
    public static final SelectableTexture CRAFTING_OVERLAY = SelectableTexture.of("crafting_overlay");
    public static final SelectableTexture CRAFTING_OVERLAY_DISABLED = SelectableTexture.of("crafting_overlay_disabled");
    public static final ButtonTextures FURNACE_FILTER = new ButtonTextures(
        recipeBookTextureIdentifier("furnace_filter_enabled"),
        recipeBookTextureIdentifier("furnace_filter"),
        recipeBookTextureIdentifier("furnace_filter_enabled_selected"),
        recipeBookTextureIdentifier("furnace_filter_selected")
    );
    public static final SelectableTexture PAGE_BACKWARD = SelectableTexture.of("page_backward");
    public static final SelectableTexture PAGE_FORWARD = SelectableTexture.of("page_forward");

    public static Identifier recipeBookTextureIdentifier(String key) {
        return Identifier.of(Hardcover.MOD_ID, "recipe_book/" + key);
    }

    public static class SelectableTexture {
        private final Identifier selectedTexture;
        private final Identifier texture;

        private SelectableTexture(String key) {
            selectedTexture = recipeBookTextureIdentifier(key + "_selected");
            texture = recipeBookTextureIdentifier(key);
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
