package io.github.dicedpixels.hardcover.mixin.recipebook;

import io.github.dicedpixels.hardcover.Hardcover;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;

@Mixin(Screen.class)
abstract class MixinScreen {
	@SuppressWarnings("CancellableInjectionUsage")
	@Inject(method = "addDrawableChild", at = @At("HEAD"), cancellable = true)
	private <T extends Element & Drawable> void hardcover$hideRecipeBookButton(T drawableElement, CallbackInfoReturnable<T> cir) {
		if (!Hardcover.CONFIG.recipeBook && drawableElement instanceof TexturedButtonWidget) {
			String path = (((AccessorTexturedButtonWidget) drawableElement)).getTexture().getPath();
			if (path.contains("textures/gui/recipe_button.png")) {
				cir.cancel();
			}
		}
	}
}
