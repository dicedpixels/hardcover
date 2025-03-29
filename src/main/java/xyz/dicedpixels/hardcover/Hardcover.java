package xyz.dicedpixels.hardcover;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.client.screen.v1.Screens;

import xyz.dicedpixels.hardcover.config.Configs;
import xyz.dicedpixels.hardcover.mixin.accessors.TexturedButtonWidgetAccessor;

public final class Hardcover {
    public static final String MOD_ID = "hardcover";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final Text TITLE = Text.literal("Hardcover");

    public static void hideRecipeBook(Screen screen) {
        if (!Configs.recipeBook.getValue() && screen instanceof RecipeBookProvider) {
            Screens.getButtons(screen).removeIf(button -> {
                if (button instanceof TexturedButtonWidget) {
                    return ((TexturedButtonWidgetAccessor) button).hardcover$getTextures() == RecipeBookWidget.BUTTON_TEXTURES;
                }

                return false;
            });
        }
    }

    public static Identifier identifierOf(String format, Object key) {
        return Identifier.of(Hardcover.MOD_ID, String.format(format, key));
    }

    public static Identifier identifierOf(String path) {
        return Identifier.of(Hardcover.MOD_ID, path);
    }
}
