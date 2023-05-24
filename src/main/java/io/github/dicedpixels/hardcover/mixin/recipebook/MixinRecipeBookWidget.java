package io.github.dicedpixels.hardcover.mixin.recipebook;

import io.github.dicedpixels.hardcover.Hardcover;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.screen.AbstractRecipeScreenHandler;

@Mixin(RecipeBookWidget.class)
abstract class MixinRecipeBookWidget {
	@Inject(method = "initialize", at = @At("TAIL"))
	private void hardcover$toggleRecipeBookOff(int parentWidth, int parentHeight, MinecraftClient client, boolean narrow, AbstractRecipeScreenHandler<?> craftingScreenHandler, CallbackInfo ci) {
		if (!Hardcover.CONFIG.recipeBook) {
			if (craftingScreenHandler != null) {
				setOpen(false);
			}
		}
	}

	@Shadow
	protected abstract void setOpen(boolean opened);
}
