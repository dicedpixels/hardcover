package xyz.dicedpixels.hardcover.mixin.alternativerecipebutton;

import java.util.Map;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.recipebook.RecipeAlternativesWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.NetworkRecipeId;
import net.minecraft.util.context.ContextParameterMap;

import xyz.dicedpixels.hardcover.config.Configs;
import xyz.dicedpixels.hardcover.contract.RecipeResultsProvider;

@Mixin(RecipeAlternativesWidget.class)
abstract class RecipeAlternativesWidgetMixin implements RecipeResultsProvider {
    @Unique
    private final Map<NetworkRecipeId, ItemStack> recipeResults = new Object2ObjectOpenHashMap<>();

    @Override
    public Map<NetworkRecipeId, ItemStack> hardcover$getRecipeResults() {
        return recipeResults;
    }

    @Inject(method = "showAlternativesForResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/recipebook/RecipeResultCollection;filter(Lnet/minecraft/client/gui/screen/recipebook/RecipeResultCollection$RecipeFilterMode;)Ljava/util/List;", ordinal = 0))
    private void hardcover$initializeRecipeResults(RecipeResultCollection resultCollection, ContextParameterMap context, boolean filteringCraftable, int buttonX, int buttonY, int areaCenterX, int areaCenterY, float delta, CallbackInfo ci) {
        if (Configs.alternativeRecipeButton.getValue()) {
            for (var displayEntry : resultCollection.getAllRecipes()) {
                recipeResults.put(displayEntry.id(), displayEntry.display().result().getFirst(context));
            }
        }
    }
}
