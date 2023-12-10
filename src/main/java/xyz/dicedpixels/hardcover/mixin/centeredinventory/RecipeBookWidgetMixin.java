package xyz.dicedpixels.hardcover.mixin.centeredinventory;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;

import xyz.dicedpixels.hardcover.Hardcover;

@Mixin(RecipeBookWidget.class)
abstract class RecipeBookWidgetMixin {
    @Shadow
    private int leftOffset;

    @Shadow
    private boolean narrow;
    // spotless:off
    @Inject(
            method = "reset",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/gui/screen/recipebook/RecipeBookWidget;leftOffset:I"))
    // spotless:on
    private void hardcover$modifyLeftOffset(CallbackInfo callbackInfo) {
        leftOffset = Hardcover.configuration().centeredInventory ? narrow ? 0 : 166 : narrow ? 0 : 86;
    }

    @ModifyReturnValue(method = "findLeftEdge", at = @At("RETURN"))
    private int hardcover$recalculateLeftEdge(int original, int width, int backgroundWidth) {
        return Hardcover.configuration().centeredInventory ? (width - backgroundWidth) / 2 : original;
    }

    @ModifyReturnValue(method = "isWide", at = @At(value = "RETURN"))
    private boolean hardcover$modifyIsWide(boolean original) {
        return Hardcover.configuration().centeredInventory && (leftOffset == 166 || leftOffset == 82) || original;
    }
}
