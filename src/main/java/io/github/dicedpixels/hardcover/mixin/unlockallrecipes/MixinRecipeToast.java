package io.github.dicedpixels.hardcover.mixin.unlockallrecipes;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.dicedpixels.hardcover.Hardcover;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.toast.RecipeToast;
import net.minecraft.client.toast.Toast.Visibility;

@Mixin(RecipeToast.class)
abstract class MixinRecipeToast {
	@ModifyReturnValue(method = "draw", at = @At("RETURN"))
	private Visibility hardcover$hideRecipeToast(Visibility original) {
		return Hardcover.CONFIG.unlockAllRecipes ? Visibility.HIDE : original;
	}
}
