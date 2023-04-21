package io.github.dicedpixels.hardcover.mixin.ungrouprecipes;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.dicedpixels.hardcover.Hardcover;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.recipebook.ClientRecipeBook;

@Mixin(ClientRecipeBook.class)
abstract class MixinClientRecipeBook {
	@ModifyExpressionValue(method = "toGroupedMap", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/Recipe;getGroup()Ljava/lang/String;"))
	private static String hardcover$ungroupRecipes(String original) {
		return Hardcover.CONFIG.ungroupRecipes ? "" : original;
	}
}
