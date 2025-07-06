package xyz.dicedpixels.hardcover.mixin.quickcraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.recipe.NetworkRecipeId;

import xyz.dicedpixels.hardcover.feature.QuickCraft;

@Mixin(ClientPlayerInteractionManager.class)
abstract class ClientPlayerInteractionManagerMixin {
    @Inject(method = "clickRecipe", at = @At("TAIL"))
    private void hardcover$setRequestedRecipeId(int syncId, NetworkRecipeId recipeId, boolean craftAll, CallbackInfo callbackInfo) {
        QuickCraft.setRequestedCraftRecipeId(recipeId);
    }
}
