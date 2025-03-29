package xyz.dicedpixels.hardcover.mixin.mousewheelscrolling;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.RecipeBookScreen;

import xyz.dicedpixels.hardcover.config.Configs;
import xyz.dicedpixels.hardcover.interfaces.MouseScrollable;
import xyz.dicedpixels.hardcover.mixin.accessors.RecipeBookScreenAccessor;
import xyz.dicedpixels.hardcover.mixin.accessors.RecipeBookWidgetAccessor;

@Mixin(HandledScreen.class)
abstract class HandledScreenMixin {
    @Unique
    private static final int RECIPE_BOOK_RIGHT_SPACING = 4;

    @Unique
    private static final int RECIPE_BOOK_TEXTURE_HEIGHT = 166;

    @Unique
    private static final int RECIPE_BOOK_TEXTURE_WIDTH = 147;

    @Inject(method = "mouseScrolled", at = @At("HEAD"))
    void hardcover$mouseScrolledInRecipeBook(double mouseX, double mouseY, double horizontalAmount, double verticalAmount, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if ((Object) this instanceof RecipeBookScreen<?> recipeBookScreen) {
            if (Configs.mouseWheelScrolling.getValue()) {
                var recipeBookWidgetAccess = (RecipeBookWidgetAccessor) ((RecipeBookScreenAccessor<?>) recipeBookScreen).hardcover$getRecipeBook();
                var recipesArea = recipeBookWidgetAccess.hardcover$getRecipesArea();
                var parentLeft = (recipeBookWidgetAccess.hardcover$getParentWidth() - RECIPE_BOOK_TEXTURE_WIDTH) / 2 - recipeBookWidgetAccess.hardcover$getLeftOffset();

                if (Configs.centeredInventory.getValue()) {
                    parentLeft = parentLeft - RECIPE_BOOK_TEXTURE_WIDTH / 2 - RECIPE_BOOK_RIGHT_SPACING;
                }

                var parentTop = (recipeBookWidgetAccess.hardcover$getParentHeight() - RECIPE_BOOK_TEXTURE_HEIGHT) / 2;

                if (mouseX >= parentLeft && mouseY >= parentTop && mouseX < parentLeft + RECIPE_BOOK_TEXTURE_WIDTH && mouseY < parentTop + RECIPE_BOOK_TEXTURE_HEIGHT) {
                    ((MouseScrollable) recipesArea).hardcover$mouseScrolled(verticalAmount);
                }
            }
        }
    }
}
