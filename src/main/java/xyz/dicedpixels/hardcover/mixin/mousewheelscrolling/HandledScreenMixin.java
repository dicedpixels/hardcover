package xyz.dicedpixels.hardcover.mixin.mousewheelscrolling;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.RecipeBookScreen;

import xyz.dicedpixels.hardcover.Config;
import xyz.dicedpixels.hardcover.interfaces.IMouseScrolled;
import xyz.dicedpixels.hardcover.mixin.accessors.RecipeBookScreenAccessor;
import xyz.dicedpixels.hardcover.mixin.accessors.RecipeBookWidgetAccessor;

@Mixin(HandledScreen.class)
abstract class HandledScreenMixin {
    @Inject(method = "mouseScrolled", at = @At("HEAD"))
    void hardcover$mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if ((Object) this instanceof RecipeBookScreen<?> recipeBookScreen) {
            if (Config.instance().mouseWheelScrolling) {
                var widget = ((RecipeBookScreenAccessor<?>) recipeBookScreen).hardcover$recipeBook();
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
