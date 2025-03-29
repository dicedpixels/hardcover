package xyz.dicedpixels.hardcover.mixin.bounce;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.gui.screen.recipebook.RecipeGroupButtonWidget;

import xyz.dicedpixels.hardcover.config.Configs;

@Mixin(RecipeGroupButtonWidget.class)
abstract class RecipeGroupButtonWidgetMixin {
    @Unique
    private static final float NEVER_BOUNCE = 1000.00f;

    @ModifyExpressionValue(method = "renderWidget", at = @At(value = "CONSTANT", args = "floatValue=0.0f", ordinal = 3))
    private float hardcover$setBounceForRecipeGroupButtonEnd(float original) {
        if (Configs.bounce.getValue()) {
            return original;
        }

        return NEVER_BOUNCE;
    }

    @ModifyExpressionValue(method = "renderWidget", at = @At(value = "CONSTANT", args = "floatValue=0.0f", ordinal = 0))
    private float hardcover$setBounceForRecipeGroupButtonStart(float original) {
        if (Configs.bounce.getValue()) {
            return original;
        }

        return NEVER_BOUNCE;
    }
}
