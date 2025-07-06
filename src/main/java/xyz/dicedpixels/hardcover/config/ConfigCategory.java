package xyz.dicedpixels.hardcover.config;

import net.minecraft.text.Text;

public enum ConfigCategory {
    RECIPE_BOOK("recipe_book"),
    RECIPES("recipes"),
    NAVIGATION("navigation"),
    FEATURES("features");

    private final String key;

    ConfigCategory(String key) {
        this.key = key;
    }

    public Text getName() {
        return Text.translatable("hardcover.gui.config.category." + key);
    }
}
