package xyz.dicedpixels.hardcover;

import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;

import xyz.dicedpixels.hardcover.mixin.recipebook.TexturedButtonWidgetAccessor;

public class Initializer implements ModInitializer {
    @Override
    public void onInitialize() {
        Hardcover.init();
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (!Hardcover.configuration().recipeBook && screen instanceof RecipeBookProvider)
                Screens.getButtons(screen).removeIf(element -> {
                    if (element instanceof TexturedButtonWidget) {
                        return ((TexturedButtonWidgetAccessor) element).hardcover$textures()
                                == RecipeBookWidget.BUTTON_TEXTURES;
                    }
                    return false;
                });
        });
    }
}
