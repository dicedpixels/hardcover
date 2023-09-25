package xyz.dicedpixels.hardcover.client.mixin.ungrouprecipes;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.recipebook.ClientRecipeBook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.dicedpixels.hardcover.Hardcover;

@Mixin(ClientRecipeBook.class)
abstract class ClientRecipeBookMixin {
    // spotless:off
    @ModifyExpressionValue(
            method = "toGroupedMap",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/recipe/Recipe;getGroup()Ljava/lang/String;"))
    // spotless:on
    private static String hardcover$setGroup(String original) {
        return Hardcover.config().ungroupRecipes ? "" : original;
    }
}
