package xyz.dicedpixels.hardcover.mixin.unlockallrecipes;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.toast.RecipeToast;
import net.minecraft.client.toast.Toast;

import xyz.dicedpixels.hardcover.Config;

@Mixin(RecipeToast.class)
abstract class RecipeToastMixin {
    @ModifyReturnValue(method = "draw", at = @At("RETURN"))
    private Toast.Visibility hardcover$hideRecipeToast(Toast.Visibility original) {
        return Config.instance().unlockAllRecipes ? Toast.Visibility.HIDE : original;
    }
}
