package io.github.dicedpixels.hardcover.mixin.circularscrolling;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.dicedpixels.hardcover.Hardcover;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.recipebook.RecipeBookResults;
import net.minecraft.client.gui.widget.ToggleButtonWidget;

@Mixin(RecipeBookResults.class)
abstract class MixinRecipeBookResults {
	@Shadow
	private int pageCount;
	@Shadow
	private int currentPage;
	@Shadow
	private ToggleButtonWidget nextPageButton;
	@Shadow
	private ToggleButtonWidget prevPageButton;

	@ModifyExpressionValue(method = "mouseClicked", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/recipebook/RecipeBookResults;currentPage:I", ordinal = 0))
	private int hardcover$incrementCurrentPage(int original) {
		return Hardcover.CONFIG.circularScrolling ? ((this.currentPage == this.pageCount - 1) ? -1 : this.currentPage) : original;
	}

	@ModifyExpressionValue(method = "mouseClicked", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/recipebook/RecipeBookResults;currentPage:I", ordinal = 2))
	private int hardcover$decrementCurrentPage(int original) {
		return Hardcover.CONFIG.circularScrolling ? ((this.currentPage == 0) ? this.pageCount : this.currentPage) : original;
	}

	@Inject(method = "hideShowPageButtons", at = @At("HEAD"), cancellable = true)
	private void hideShowPageButtons(CallbackInfo ci) {
		if (Hardcover.CONFIG.circularScrolling) {
			this.nextPageButton.visible = this.pageCount > 1;
			this.prevPageButton.visible = this.pageCount > 1;
			ci.cancel();
		}
	}
}
