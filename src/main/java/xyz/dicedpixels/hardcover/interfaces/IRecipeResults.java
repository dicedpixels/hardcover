package xyz.dicedpixels.hardcover.interfaces;

import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.NetworkRecipeId;

public interface IRecipeResults {
    Map<NetworkRecipeId, ItemStack> hardcover$getRecipeResults();
}
