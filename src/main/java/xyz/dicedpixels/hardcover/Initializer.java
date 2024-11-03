package xyz.dicedpixels.hardcover;

import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;

import xyz.dicedpixels.hardcover.mixin.accessors.TexturedButtonWidgetAccessor;

public class Initializer implements ModInitializer {
    @Override
    public void onInitialize() {
        ScreenEvents.AFTER_INIT.register((minecraftClient, screen, scaledWidth, scaledHeight) -> {
            if (!Config.instance().recipeBook && screen instanceof RecipeBookProvider) {
                Screens.getButtons(screen).removeIf(button -> {
                    if (button instanceof TexturedButtonWidget) {
                        return ((TexturedButtonWidgetAccessor) button).hardcover$textures() == RecipeBookWidget.BUTTON_TEXTURES;
                    }

                    return false;
                });
            }
        });
    }
}
