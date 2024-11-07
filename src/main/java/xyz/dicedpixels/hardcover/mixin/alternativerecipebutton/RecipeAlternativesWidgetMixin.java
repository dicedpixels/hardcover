package xyz.dicedpixels.hardcover.mixin.alternativerecipebutton;

import java.util.Map;
import java.util.stream.Collectors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.recipebook.RecipeAlternativesWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.NetworkRecipeId;
import net.minecraft.recipe.RecipeDisplayEntry;
import net.minecraft.util.context.ContextParameterMap;

import xyz.dicedpixels.hardcover.interfaces.IRecipeResults;

@Mixin(RecipeAlternativesWidget.class)
abstract class RecipeAlternativesWidgetMixin implements IRecipeResults {
    @Unique
    private Map<NetworkRecipeId, ItemStack> recipeResults = Map.of();

    @Override
    public Map<NetworkRecipeId, ItemStack> hardcover$getRecipeResults() {
        return recipeResults;
    }

    @Inject(method = "showAlternativesForResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/recipebook/RecipeResultCollection;filter(Lnet/minecraft/client/gui/screen/recipebook/RecipeResultCollection$RecipeFilterMode;)Ljava/util/List;", ordinal = 0))
    private void hardcover$initializeRecipeResultMap(RecipeResultCollection resultCollection, ContextParameterMap context, boolean filteringCraftable, int buttonX, int buttonY, int areaCenterX, int areaCenterY, float delta, CallbackInfo callbackInfo) {
        recipeResults = resultCollection.getAllRecipes().stream().collect(Collectors.toMap(RecipeDisplayEntry::id, entry -> entry.display().result().getFirst(context)));
    }
}
