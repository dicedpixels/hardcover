package xyz.dicedpixels.hardcover.mixin.circularscrolling;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.recipebook.RecipeBookResults;
import net.minecraft.client.gui.widget.ToggleButtonWidget;

import xyz.dicedpixels.hardcover.Hardcover;

@Mixin(RecipeBookResults.class)
abstract class RecipeBookResultsMixin {
    @Shadow
    private int pageCount;

    @Shadow
    private int currentPage;

    @Shadow
    private ToggleButtonWidget nextPageButton;

    @Shadow
    private ToggleButtonWidget prevPageButton;
    // spotless:off
    @ModifyExpressionValue(
            method = "mouseClicked",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/gui/screen/recipebook/RecipeBookResults;currentPage:I",
                    ordinal = 0))
    // spotless:on
    private int hardcover$incrementCurrentPage(int original) {
        return Hardcover.configuration().circularScrolling
                ? ((currentPage == pageCount - 1) ? -1 : currentPage)
                : original;
    }
    // spotless:off
    @ModifyExpressionValue(
            method = "mouseClicked",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/gui/screen/recipebook/RecipeBookResults;currentPage:I",
                    ordinal = 2))
    // spotless:on
    private int hardcover$decrementCurrentPage(int original) {
        return Hardcover.configuration().circularScrolling ? ((currentPage == 0) ? pageCount : currentPage) : original;
    }

    @Inject(method = "hideShowPageButtons", at = @At("HEAD"), cancellable = true)
    private void hardcover$hideShowPageButtons(CallbackInfo callbackInfo) {
        if (Hardcover.configuration().circularScrolling) {
            nextPageButton.visible = pageCount > 1;
            prevPageButton.visible = pageCount > 1;

            callbackInfo.cancel();
        }
    }
}
