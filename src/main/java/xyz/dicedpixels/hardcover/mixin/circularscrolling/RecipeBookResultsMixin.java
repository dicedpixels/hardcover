package xyz.dicedpixels.hardcover.mixin.circularscrolling;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.recipebook.RecipeBookResults;
import net.minecraft.client.gui.widget.ToggleButtonWidget;

import xyz.dicedpixels.hardcover.config.Configs;

@Mixin(RecipeBookResults.class)
abstract class RecipeBookResultsMixin {
    @Shadow
    private int currentPage;

    @Shadow
    private ToggleButtonWidget nextPageButton;

    @Shadow
    private int pageCount;

    @Shadow
    private ToggleButtonWidget prevPageButton;

    @ModifyExpressionValue(method = "mouseClicked", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/recipebook/RecipeBookResults;currentPage:I", ordinal = 2))
    private int hardcover$decrementCurrentPage(int original) {
        if (Configs.circularScrolling.getValue()) {
            if (currentPage == 0) {
                return pageCount;
            }

            return currentPage;
        }

        return original;
    }

    @ModifyExpressionValue(method = "mouseClicked", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/recipebook/RecipeBookResults;currentPage:I", ordinal = 0))
    private int hardcover$incrementCurrentPage(int original) {
        if (Configs.circularScrolling.getValue()) {
            if (currentPage == pageCount - 1) {
                return -1;
            }

            return currentPage;
        }

        return original;
    }

    @Inject(method = "hideShowPageButtons", at = @At("HEAD"), cancellable = true)
    private void hardcover$setNextPrevButtonVisibility(CallbackInfo callbackInfo) {
        if (Configs.circularScrolling.getValue()) {
            nextPageButton.visible = pageCount > 1;
            prevPageButton.visible = pageCount > 1;
            callbackInfo.cancel();
        }
    }
}
