package xyz.dicedpixels.hardcover;

import net.minecraft.util.Identifier;

public final class TextureProvider {
    public static final SelectableTexture CRAFTING_FILTER = SelectableTexture.of("crafting_filter");
    public static final SelectableTexture CRAFTING_FILTER_ENABLED = SelectableTexture.of("crafting_filter_enabled");
    public static final SelectableTexture CRAFTING_GRID = SelectableTexture.of("crafting_grid");
    public static final SelectableTexture CRAFTING_OVERLAY = SelectableTexture.of("crafting_overlay");
    public static final SelectableTexture CRAFTING_OVERLAY_DISABLED = SelectableTexture.of("crafting_overlay_disabled");
    public static final SelectableTexture FURNACE_FILTER = SelectableTexture.of("furnace_filter");
    public static final SelectableTexture FURNACE_FILTER_ENABLED = SelectableTexture.of("furnace_filter_enabled");
    public static final SelectableTexture PAGE_BACKWARD = SelectableTexture.of("page_backward");
    public static final SelectableTexture PAGE_FORWARD = SelectableTexture.of("page_forward");

    public static final class SelectableTexture {
        private final Identifier selectedTexture;
        private final Identifier texture;

        private SelectableTexture(String key) {
            selectedTexture = Hardcover.identifierOf("recipe_book/%s_selected", key);
            texture = Hardcover.identifierOf("recipe_book/%s", key);
        }

        public static SelectableTexture of(String key) {
            return new SelectableTexture(key);
        }

        public Identifier getSelectedTexture() {
            return selectedTexture;
        }

        public Identifier getTexture(boolean isSelected) {
            return isSelected ? selectedTexture : texture;
        }

        public Identifier getTexture() {
            return texture;
        }
    }
}
