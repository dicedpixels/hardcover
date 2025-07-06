package xyz.dicedpixels.hardcover.mixin.alternativerecipebooklayout;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.recipebook.RecipeBookResults;
import net.minecraft.client.gui.widget.ToggleButtonWidget;

import xyz.dicedpixels.hardcover.config.Configs;
import xyz.dicedpixels.hardcover.gui.Textures;
import xyz.dicedpixels.hardcover.gui.Textures.SelectableTexture;

@Mixin(RecipeBookResults.class)
abstract class RecipeBookResultsMixin {
    @Shadow
    private ToggleButtonWidget nextPageButton;

    @Shadow
    private ToggleButtonWidget prevPageButton;

    @Unique
    private ToggleButtonWidget hardcover$createToggleButtonWidget(int x, int parentTop, boolean isToggled, SelectableTexture texture) {
        var buttonWidget = new ToggleButtonWidget(x, parentTop + 12, 7, 16, isToggled);

        buttonWidget.setTextures(texture.asButtonTextures());
        return buttonWidget;
    }

    @ModifyExpressionValue(method = "*", at = @At(value = "CONSTANT", args = "intValue=20"))
    private int hardcover$increaseItemsPerPage(int original) {
        if (Configs.alternativeRecipeBookLayout.getValue()) {
            return 25;
        }

        return original;
    }

    @ModifyExpressionValue(method = "setResults", at = @At(value = "CONSTANT", args = "doubleValue=20.0"))
    private double hardcover$increaseItemsPerPage(double original) {
        if (Configs.alternativeRecipeBookLayout.getValue()) {
            return 25;
        }

        return original;
    }

    @Inject(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ToggleButtonWidget;<init>(IIIIZ)V", ordinal = 0), cancellable = true)
    private void hardcover$replaceNextPreviousButtons(MinecraftClient client, int parentLeft, int parentTop, CallbackInfo callbackInfo) {
        if (Configs.alternativeRecipeBookLayout.getValue()) {
            nextPageButton = hardcover$createToggleButtonWidget(parentLeft + 129, parentTop, false, Textures.PAGE_FORWARD);
            prevPageButton = hardcover$createToggleButtonWidget(parentLeft + 120, parentTop, true, Textures.PAGE_BACKWARD);

            callbackInfo.cancel();
        }
    }

    @ModifyExpressionValue(method = "draw", at = @At(value = "CONSTANT", args = "intValue=1"))
    private int hardcover$setPageCountToHideText(int original) {
        if (Configs.alternativeRecipeBookLayout.getValue()) {
            return 1000;
        }

        return original;
    }
}
