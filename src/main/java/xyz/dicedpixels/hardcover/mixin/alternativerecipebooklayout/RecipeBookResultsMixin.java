package xyz.dicedpixels.hardcover.mixin.alternativerecipebooklayout;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.recipebook.RecipeBookResults;
import net.minecraft.client.gui.widget.ToggleButtonWidget;

import xyz.dicedpixels.hardcover.TextureProvider;
import xyz.dicedpixels.hardcover.TextureProvider.SelectableTexture;
import xyz.dicedpixels.hardcover.config.Configs;

@Mixin(RecipeBookResults.class)
abstract class RecipeBookResultsMixin {

    @Unique
    private static final int ITEMS_PER_PAGE = 25;

    @Shadow
    private ToggleButtonWidget nextPageButton;

    @Shadow
    private ToggleButtonWidget prevPageButton;

    @ModifyExpressionValue(method = "draw", at = @At(value = "CONSTANT", args = "intValue=1"))
    private int hardcover$hideRecipePagesCountText(int original) {
        if (Configs.alternativeRecipeBookLayout.getValue()) {
            return 1000;
        }

        return original;
    }

    @ModifyExpressionValue(method = "setResults", at = @At(value = "CONSTANT", args = "doubleValue=20.0"))
    private double hardcover$increaseItemsPerPage(double original) {
        if (Configs.alternativeRecipeBookLayout.getValue()) {
            return ITEMS_PER_PAGE;
        }

        return original;
    }

    @ModifyExpressionValue(method = "*", at = @At(value = "CONSTANT", args = "intValue=20"))
    private int hardcover$increaseItemsPerPage(int original) {
        if (Configs.alternativeRecipeBookLayout.getValue()) {
            return ITEMS_PER_PAGE;
        }

        return original;
    }

    @Unique
    private ToggleButtonWidget hardcover$initializeToggleButtonWidget(int x, int parentTop, boolean isToggled, SelectableTexture texture) {
        var buttonWidget = new ToggleButtonWidget(x, parentTop + 12, 7, 16, isToggled);
        buttonWidget.setTextures(new ButtonTextures(texture.getTexture(), texture.getSelectedTexture()));

        return buttonWidget;
    }

    @Inject(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ToggleButtonWidget;<init>(IIIIZ)V", ordinal = 0), cancellable = true)
    private void hardcover$replaceNextPreviousButtons(MinecraftClient client, int parentLeft, int parentTop, CallbackInfo callbackInfo) {
        if (Configs.alternativeRecipeBookLayout.getValue()) {
            nextPageButton = hardcover$initializeToggleButtonWidget(parentLeft + 129, parentTop, false, TextureProvider.PAGE_FORWARD);
            prevPageButton = hardcover$initializeToggleButtonWidget(parentLeft + 120, parentTop, true, TextureProvider.PAGE_BACKWARD);

            callbackInfo.cancel();
        }
    }
}
