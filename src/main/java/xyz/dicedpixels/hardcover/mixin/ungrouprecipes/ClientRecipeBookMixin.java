package xyz.dicedpixels.hardcover.mixin.ungrouprecipes;

import java.util.OptionalInt;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.recipebook.ClientRecipeBook;

import xyz.dicedpixels.hardcover.Config;

@Mixin(ClientRecipeBook.class)
abstract class ClientRecipeBookMixin {
    @ModifyExpressionValue(method = "toGroupedMap", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/RecipeDisplayEntry;group()Ljava/util/OptionalInt;"))
    private static OptionalInt hardcover$setGroup(OptionalInt original) {
        return Config.instance().ungroupRecipes ? OptionalInt.empty() : original;
    }
}
