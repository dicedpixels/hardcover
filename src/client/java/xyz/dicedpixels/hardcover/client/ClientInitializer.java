package xyz.dicedpixels.hardcover.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import xyz.dicedpixels.hardcover.Hardcover;
import xyz.dicedpixels.hardcover.client.mixin.recipebook.TexturedButtonWidgetAccessor;

public class ClientInitializer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (!Hardcover.config().recipeBook && screen instanceof RecipeBookProvider)
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
