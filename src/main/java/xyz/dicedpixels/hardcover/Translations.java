package xyz.dicedpixels.hardcover;

import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.text.Text;

public class Translations {
    public static final Pair ALTERNATIVE_RECIPE_BUTTON = Pair.of("alternative_recipe_button");
    public static final Pair BOUNCE = Pair.of("bounce");
    public static final Pair CENTERED_INVENTORY = Pair.of("centered_inventory");
    public static final Pair CIRCULAR_SCROLLING = Pair.of("circular_scrolling");
    public static final Pair DARK_MODE = Pair.of("dark_mode");
    public static final Text DONE = Text.translatable("hardcover.gui.done");
    public static final Pair MOUSE_WHEEL_SCROLLING = Pair.of("mouse_wheel_scrolling");
    public static final Text OFF = Text.translatable("hardcover.gui.off");
    public static final Text ON = Text.translatable("hardcover.gui.on");
    public static final Pair RECIPE_BOOK = Pair.of("recipe_book");
    public static final Text RESET = Text.translatable("hardcover.gui.reset");
    public static final Pair UNGROUP_RECIPES = Pair.of("ungroup_recipes");
    public static final Pair UNLOCK_ALL_RECIPES = Pair.of("unlock_all_recipes");

    public static class Pair {
        private final Text message;
        private final Text tooltip;

        private Pair(Text message, Text tooltip) {
            this.message = message;
            this.tooltip = tooltip;
        }

        public static Pair of(String property) {
            return new Pair(Text.translatable(String.format("hardcover.gui.%s", property)), Text.translatable(String.format("hardcover.gui.%s.tooltip", property)));
        }

        public Text getMessage() {
            return message;
        }

        public Tooltip getTooltip() {
            return Tooltip.of(tooltip);
        }
    }
}
