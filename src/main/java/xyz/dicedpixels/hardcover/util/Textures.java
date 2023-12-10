package xyz.dicedpixels.hardcover.util;

import net.minecraft.util.Identifier;

import xyz.dicedpixels.hardcover.Hardcover;

public class Textures {
    public static final Pair WIDGET = of("widget");
    public static final Pair WIDGET_SELECTED = of("widget_selected");
    public static final Pair WIDGET_DISABLED = of("widget_disabled");
    public static final Pair WIDGET_DISABLED_SELECTED = of("widget_disabled_selected");
    public static final Pair CRAFTING_GRID = of("crafting_grid");

    private static Pair of(String path) {
        return new Pair(
                new Identifier("hardcover", String.format("recipe_book/%s", path)),
                new Identifier("hardcover", String.format("recipe_book/%s_dark", path)));
    }

    public record Pair(Identifier vanilla, Identifier dark) {
        public Identifier get() {
            return Hardcover.configuration().darkMode ? dark : vanilla;
        }
    }
}
