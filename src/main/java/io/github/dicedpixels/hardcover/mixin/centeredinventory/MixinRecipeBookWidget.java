package io.github.dicedpixels.hardcover.mixin.centeredinventory;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.dicedpixels.hardcover.Hardcover;
import io.github.dicedpixels.hardcover.config.HardcoverConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;

@Mixin(RecipeBookWidget.class)
abstract class MixinRecipeBookWidget {
	@Shadow
	private int leftOffset;
	@Shadow
	private boolean narrow;

	@Inject(method = "reset", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/recipebook/RecipeBookWidget;leftOffset:I"))
	private void hardcover$modifyLeftOffset(CallbackInfo ci) {
		this.leftOffset = Hardcover.CONFIG.centeredInventory ? this.narrow ? 0 : 166 : this.narrow ? 0 : 86;
	}

	@ModifyReturnValue(method = "findLeftEdge", at = @At("RETURN"))
	private int hardcover$recalculateLeftEdge(int original, int width, int backgroundWidth) {
		if (HardcoverConfig.getConfig().centeredInventory) {
			width = (width - backgroundWidth) / 2;
			return width;
		}
		return original;
	}

	@ModifyReturnValue(method = "isWide", at = @At(value = "RETURN"))
	private boolean hardcover$modifyIsWide(boolean original) {
		return Hardcover.CONFIG.centeredInventory && (this.leftOffset == 166 || this.leftOffset == 82) || original;
	}
}
