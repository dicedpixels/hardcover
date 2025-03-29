package xyz.dicedpixels.hardcover.mixin.quickcraft;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.recipe.NetworkRecipeId;

import xyz.dicedpixels.hardcover.util.QuickCraft;

@Mixin(RecipeBookWidget.class)
abstract class RecipeBookWidgetMixin {
    @ModifyExpressionValue(method = "mouseClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/recipebook/RecipeBookResults;getLastClickedRecipe()Lnet/minecraft/recipe/NetworkRecipeId;"))
    private NetworkRecipeId hardcover$setClickedRecipeId(NetworkRecipeId original) {
        QuickCraft.clickedRecipeId = original;
        return original;
    }
}
