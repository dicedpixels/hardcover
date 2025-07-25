package xyz.dicedpixels.hardcover.mixin.mousewheelscrolling;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.RecipeBookScreen;

import xyz.dicedpixels.hardcover.config.Configs;
import xyz.dicedpixels.hardcover.contract.MouseScrollable;
import xyz.dicedpixels.hardcover.mixin.accessors.RecipeBookScreenAccessor;
import xyz.dicedpixels.hardcover.mixin.accessors.RecipeBookWidgetAccessor;

@Mixin(HandledScreen.class)
abstract class HandledScreenMixin {
    @Inject(method = "mouseScrolled", at = @At("HEAD"))
    void hardcover$mouseScrolledInRecipeBook(double mouseX, double mouseY, double horizontalAmount, double verticalAmount, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if ((Object) this instanceof RecipeBookScreen<?> recipeBookScreen) {
            if (Configs.mouseWheelScrolling.getValue()) {
                var recipeBookWidget = ((RecipeBookScreenAccessor<?>) recipeBookScreen).hardcover$getRecipeBook();
                var recipeBookWidgetAccess = (RecipeBookWidgetAccessor) recipeBookWidget;
                var parentLeft = (recipeBookWidgetAccess.hardcover$getParentWidth() - 147) / 2 - recipeBookWidgetAccess.hardcover$getLeftOffset();

                if (Configs.centeredInventory.getValue()) {
                    parentLeft = parentLeft - 147 / 2 - 4;
                }

                var parentTop = (recipeBookWidgetAccess.hardcover$getParentHeight() - 166) / 2;

                if (mouseX >= parentLeft && mouseY >= parentTop && mouseX < parentLeft + 147 && mouseY < parentTop + 166) {
                    ((MouseScrollable) recipeBookWidgetAccess.hardcover$getRecipesArea()).hardcover$mouseScrolled(verticalAmount);
                }

                if (Configs.creativeTabs.getValue()) {
                    var xOffset = 0;
                    var yOffset = 0;

                    if (Configs.compactCreativeTabs.getValue()) {
                        xOffset = 22;
                        yOffset = 3;
                    } else {
                        xOffset = 30;
                        yOffset = 29;
                    }

                    if (mouseX >= parentLeft - xOffset && mouseX <= parentLeft && mouseY >= parentTop + 3 && mouseY <= parentTop + 166 - yOffset) {
                        ((MouseScrollable) recipeBookWidget).hardcover$mouseScrolled(verticalAmount);
                    }
                }
            }
        }
    }
}
