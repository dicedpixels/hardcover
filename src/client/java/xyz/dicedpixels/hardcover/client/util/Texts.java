package xyz.dicedpixels.hardcover.client.util;

import net.minecraft.text.Text;
import xyz.dicedpixels.hardcover.Hardcover;

public class Texts {
    public static final TextPair<Text> ALTERNATIVE_RECIPE_BUTTON = text("alternative_recipe_button");
    public static final TextPair<Text> DARK_MODE = text("dark_mode");
    public static final TextPair<Text> UNGROUP_RECIPES = text("ungroup_recipes");
    public static final TextPair<Text> RECIPE_BOOK = text("recipe_book");
    public static final TextPair<Text> UNLOCK_ALL_RECIPES = text("unlock_all_recipes");
    public static final TextPair<Text> BOUNCE = text("bounce");
    public static final TextPair<Text> CIRCULAR_SCROLLING = text("circular_scrolling");
    public static final TextPair<Text> CENTERED_INVENTORY = text("centered_inventory");
    public static final TextPair<Text> MOUSE_WHEEL_SCROLLING = text("mouse_wheel_scrolling");

    public static TextPair<Text> text(String key) {
        return new TextPair<>(
                Text.translatable(String.format("%s.option.%s", Hardcover.MOD_ID, key)),
                Text.translatable(String.format("%s.option.tooltip.%s", Hardcover.MOD_ID, key)));
    }

    public record TextPair<T>(T display, T tooltip) {}
}
