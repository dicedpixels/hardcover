package xyz.dicedpixels.hardcover;

import net.minecraft.util.Identifier;

public class Textures {
    public static final Pair CRAFTING_GRID = Pair.of("crafting_grid");
    public static final Pair WIDGET = Pair.of("widget");
    public static final Pair WIDGET_DISABLED = Pair.of("widget_disabled");
    public static final Pair WIDGET_DISABLED_SELECTED = Pair.of("widget_disabled_selected");
    public static final Pair WIDGET_SELECTED = Pair.of("widget_selected");

    public static class Pair {
        private final Identifier dark;
        private final Identifier vanilla;

        private Pair(Identifier vanilla, Identifier dark) {
            this.vanilla = vanilla;
            this.dark = dark;
        }

        private static Identifier identifier(String path, Object... args) {
            return Identifier.of("hardcover", String.format(path, args));
        }

        public static Pair of(String resource) {
            return new Pair(identifier("recipe_book/vanilla/%s", resource), identifier("recipe_book/dark/%s", resource));
        }

        public Identifier get() {
            return Config.instance().darkMode ? dark : vanilla;
        }
    }
}
