package xyz.dicedpixels.hardcover.mixin.bounce;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.gui.screen.recipebook.AnimatedResultButton;

import xyz.dicedpixels.hardcover.config.Configs;

@Mixin(AnimatedResultButton.class)
abstract class AnimatedResultButtonMixin {
    @ModifyExpressionValue(method = "showResultCollection", at = @At(value = "CONSTANT", args = "floatValue=15.0"))
    private float hardcover$setBounceForRecipeResultButtons(float original) {
        if (Configs.bounce.getValue()) {
            return original;
        }

        return 0;
    }
}
