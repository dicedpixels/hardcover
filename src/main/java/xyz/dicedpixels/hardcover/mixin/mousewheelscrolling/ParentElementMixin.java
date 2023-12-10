package xyz.dicedpixels.hardcover.mixin.mousewheelscrolling;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.gui.ParentElement;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;

import xyz.dicedpixels.hardcover.Hardcover;
import xyz.dicedpixels.hardcover.contract.IMouseScrolled;

@Mixin(ParentElement.class)
public interface ParentElementMixin {
    @Inject(method = "mouseScrolled", at = @At("HEAD"))
    default void hardcover$mouseScrolled(
            double mouseX,
            double mouseY,
            double horizontalAmount,
            double verticalAmount,
            CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof HandledScreen<?> && this instanceof RecipeBookProvider recipeBookProvider) {
            if (Hardcover.configuration().mouseWheelScrolling) {
                var widget =
                        ((RecipeBookWidgetAccessor) recipeBookProvider.getRecipeBookWidget()).hardcover$recipesArea();
                ((IMouseScrolled) widget).hardcover$mouseScrolled(verticalAmount);
            }
        }
    }
}
