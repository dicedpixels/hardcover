package xyz.dicedpixels.hardcover.feature;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;

import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;

import xyz.dicedpixels.hardcover.config.Configs;
import xyz.dicedpixels.hardcover.mixin.accessors.TexturedButtonWidgetAccessor;
import xyz.dicedpixels.hardcover.mixin.invokers.ClientPlayNetworkHandlerInvoker;

public final class RecipeBook {
    private static void hideRecipeBook(Screen screen) {
        if (!Configs.recipeBook.getValue() && screen instanceof RecipeBookProvider) {
            Screens.getButtons(screen).removeIf(button -> {
                if (button instanceof TexturedButtonWidget) {
                    return ((TexturedButtonWidgetAccessor) button).hardcover$getTextures() == RecipeBookWidget.BUTTON_TEXTURES;
                }

                return false;
            });
        }
    }

    public static void init() {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> hideRecipeBook(screen));
    }

    public static void refreshRecipeBook(MinecraftClient client) {
        if (client != null && client.player != null && client.world != null && client.getNetworkHandler() != null) {
            client.player.getRecipeBook().refresh();
            client.getNetworkHandler().refreshSearchManager();
            ((ClientPlayNetworkHandlerInvoker) client.getNetworkHandler()).hardcover$refreshRecipeBook(client.player.getRecipeBook());
        }
    }
}
