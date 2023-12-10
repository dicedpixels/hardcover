package xyz.dicedpixels.hardcover.util;

import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class Texts {
    public static final Pair ALTERNATIVE_RECIPE_BUTTON = of("alternative-recipe-button");
    public static final Pair DARK_MODE = of("dark-mode");
    public static final Pair UNGROUP_RECIPES = of("ungroup-recipes");
    public static final Pair RECIPE_BOOK = of("recipe-book");
    public static final Pair UNLOCK_ALL_RECIPES = of("unlock-all-recipes");
    public static final Pair BOUNCE = of("bounce");
    public static final Pair CIRCULAR_SCROLLING = of("circular-scrolling");
    public static final Pair CENTERED_INVENTORY = of("centered-inventory");
    public static final Pair MOUSE_WHEEL_SCROLLING = of("mouse-wheel-scrolling");
    public static final Text DONE = Text.translatable("hardcover.done");
    public static final Text RESET = Text.translatable("hardcover.reset");
    public static final Text EXPERIMENTAL_TOOLTIP = ScreenTexts.LINE_BREAK
            .copy()
            .append(Text.translatable("hardcover.experimental-tooltip").formatted(Formatting.RED));

    public static Pair of(String key) {
        return new Pair(
                Text.translatable(String.format("%s.%s", "hardcover", key)),
                Text.translatable(String.format("%s.tooltip.%s", "hardcover", key)));
    }

    public record Pair(MutableText display, MutableText tooltip) {}
}
