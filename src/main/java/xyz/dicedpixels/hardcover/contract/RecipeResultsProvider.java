package xyz.dicedpixels.hardcover.contract;

import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.NetworkRecipeId;

public interface RecipeResultsProvider {
    Map<NetworkRecipeId, ItemStack> hardcover$getRecipeResults();
}
