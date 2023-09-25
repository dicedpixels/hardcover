package xyz.dicedpixels.hardcover.client.util;

import net.minecraft.util.Identifier;
import xyz.dicedpixels.hardcover.Hardcover;

public class Textures {
    public static final TexturePair<Identifier> WIDGET = texture("widget");
    public static final TexturePair<Identifier> WIDGET_SELECTED = texture("widget_selected");
    public static final TexturePair<Identifier> WIDGET_DISABLED = texture("widget_disabled");
    public static final TexturePair<Identifier> WIDGET_DISABLED_SELECTED = texture("widget_disabled_selected");

    private static TexturePair<Identifier> texture(String path) {
        return new TexturePair<>(
                new Identifier(Hardcover.MOD_ID, String.format("recipe_book/%s", path)),
                new Identifier(Hardcover.MOD_ID, String.format("recipe_book/%s_dark", path)));
    }

    public record TexturePair<T>(T vanilla, T dark) {
        public T get() {
            return !Hardcover.config().darkMode ? vanilla : dark;
        }
    }
}
