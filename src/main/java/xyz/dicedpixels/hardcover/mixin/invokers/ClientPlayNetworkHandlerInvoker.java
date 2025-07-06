package xyz.dicedpixels.hardcover.mixin.invokers;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.recipebook.ClientRecipeBook;

@Mixin(ClientPlayNetworkHandler.class)
public interface ClientPlayNetworkHandlerInvoker {
    @Invoker("refreshRecipeBook")
    void hardcover$refreshRecipeBook(ClientRecipeBook recipeBook);
}
