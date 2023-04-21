package io.github.dicedpixels.hardcover.mixin.bounce;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.dicedpixels.hardcover.Hardcover;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.recipe.book.RecipeBook;

@Mixin(RecipeBook.class)
abstract class MixinRecipeBook {
	@ModifyReturnValue(method = "shouldDisplay", at = @At("RETURN"))
	private boolean hardcover$toggleBounce(boolean original) {
		return Hardcover.CONFIG.bounce && original;
	}
}
