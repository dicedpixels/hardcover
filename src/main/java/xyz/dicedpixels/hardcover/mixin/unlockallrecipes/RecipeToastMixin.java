package xyz.dicedpixels.hardcover.mixin.unlockallrecipes;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.toast.RecipeToast;
import net.minecraft.client.toast.Toast.Visibility;

import xyz.dicedpixels.hardcover.Hardcover;

@Mixin(RecipeToast.class)
abstract class RecipeToastMixin {
    @ModifyReturnValue(method = "draw", at = @At("RETURN"))
    private Visibility hardcover$hideRecipeToast(Visibility original) {
        return Hardcover.configuration().unlockAllRecipes ? Visibility.HIDE : original;
    }
}
