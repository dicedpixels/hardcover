package xyz.dicedpixels.hardcover.mixin.bounce;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.gui.screen.recipebook.RecipeGroupButtonWidget;

import xyz.dicedpixels.hardcover.Config;

@Mixin(RecipeGroupButtonWidget.class)
abstract class RecipeGroupButtonWidgetMixin {
    @ModifyExpressionValue(method = "renderWidget", at = @At(value = "CONSTANT", args = "floatValue=0.0f", ordinal = 3))
    private float hardcover$toggleBounceEnd(float original) {
        return Config.instance().bounce ? original : 1000.00f;
    }

    @ModifyExpressionValue(method = "renderWidget", at = @At(value = "CONSTANT", args = "floatValue=0.0f", ordinal = 0))
    private float hardcover$toggleBounceStart(float original) {
        return Config.instance().bounce ? original : 1000.00f;
    }
}
