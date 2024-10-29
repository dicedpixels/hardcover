package xyz.dicedpixels.hardcover.mixin.mousewheelscrolling;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.gui.ParentElement;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;

import xyz.dicedpixels.hardcover.Config;
import xyz.dicedpixels.hardcover.IMouseScrolled;
import xyz.dicedpixels.hardcover.mixin.accessors.RecipeBookWidgetAccessor;

@Mixin(ParentElement.class)
interface ParentElementMixin {
    @Inject(method = "mouseScrolled", at = @At("HEAD"))
    default void hardcover$mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (this instanceof HandledScreen<?> && this instanceof RecipeBookProvider recipeBookProvider) {
            if (Config.instance().mouseWheelScrolling) {
                var widget = recipeBookProvider.getRecipeBookWidget();
                var recipesArea = ((RecipeBookWidgetAccessor) widget).hardcover$recipesArea();
                var parentLeft = (((RecipeBookWidgetAccessor) widget).hardcover$parentWidth() - 147) / 2 - ((RecipeBookWidgetAccessor) widget).hardcover$leftOffset();
                var parentTop = (((RecipeBookWidgetAccessor) widget).hardcover$parentHeight() - 166) / 2;

                if (mouseX >= parentLeft && mouseY >= parentTop && mouseX < parentLeft + 147 && mouseY < parentTop + 166) {
                    ((IMouseScrolled) recipesArea).hardcover$mouseScrolled(verticalAmount);
                }
            }
        }
    }
}
