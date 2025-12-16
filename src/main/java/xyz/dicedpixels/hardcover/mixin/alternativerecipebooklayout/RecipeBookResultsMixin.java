package xyz.dicedpixels.hardcover.mixin.alternativerecipebooklayout;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.recipebook.RecipeBookResults;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.text.Text;

import xyz.dicedpixels.hardcover.config.Configs;
import xyz.dicedpixels.hardcover.gui.Textures;
import xyz.dicedpixels.hardcover.gui.Textures.SelectableTexture;

@Mixin(RecipeBookResults.class)
abstract class RecipeBookResultsMixin {
    @Shadow
    @Final
    private static Text NEXT_PAGE_TOOLTIP;

    @Shadow
    @Final
    private static Text PREVIOUS_PAGE_TOOLTIP;

    @Shadow
    private TexturedButtonWidget nextPageButton;

    @Shadow
    private TexturedButtonWidget prevPageButton;

    @Unique
    private TexturedButtonWidget hardcover$createTexturedButtonWidget(int x, int parentTop, SelectableTexture texture, Text tooltip) {
        var buttonWidget = new TexturedButtonWidget(7, 16, texture.asButtonTextures(), button -> this.hideShowPageButtons(), tooltip);

        buttonWidget.setPosition(x, parentTop + 12);
        return buttonWidget;
    }

    @Inject(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TexturedButtonWidget;<init>(IIIILnet/minecraft/client/gui/screen/ButtonTextures;Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;Lnet/minecraft/text/Text;)V", ordinal = 0), cancellable = true)
    private void hardcover$replaceNextPreviousButtons(MinecraftClient client, int parentLeft, int parentTop, CallbackInfo callbackInfo) {
        if (Configs.alternativeRecipeBookLayout.getValue()) {
            nextPageButton = hardcover$createTexturedButtonWidget(parentLeft + 129, parentTop, Textures.PAGE_FORWARD, NEXT_PAGE_TOOLTIP);
            prevPageButton = hardcover$createTexturedButtonWidget(parentLeft + 120, parentTop, Textures.PAGE_BACKWARD, PREVIOUS_PAGE_TOOLTIP);

            callbackInfo.cancel();
        }
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

    @Shadow
    protected abstract void hideShowPageButtons();

    @ModifyExpressionValue(method = "draw", at = @At(value = "CONSTANT", args = "intValue=1"))
    private int hardcover$setPageCountToHideText(int original) {
        if (Configs.alternativeRecipeBookLayout.getValue()) {
            return 1000;
        }

        return original;
    }
}
