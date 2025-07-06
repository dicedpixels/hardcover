package xyz.dicedpixels.hardcover.mixin.creativetabs;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.world.ClientWorld;

import xyz.dicedpixels.hardcover.config.Configs;
import xyz.dicedpixels.hardcover.feature.CreativeTabs;

@Mixin(ClientPlayNetworkHandler.class)
abstract class ClientPlayNetworkHandlerMixin {
    @Shadow
    private ClientWorld world;

    @Inject(method = "refreshRecipeBook", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/search/SearchManager;addRecipeOutputReloader(Lnet/minecraft/client/recipebook/ClientRecipeBook;Lnet/minecraft/world/World;)V"))
    private void hardcover$groupCreativeTabsOrderedResults(ClientRecipeBook recipeBook, CallbackInfo callbackInfo) {
        if (Configs.creativeTabs.getValue()) {
            CreativeTabs.groupOrderedResults(world, recipeBook.getOrderedResults());
        }
    }
}
