package io.github.dicedpixels.hardcover.mixin.unlockallrecipes;

import io.github.dicedpixels.hardcover.Hardcover;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;

@Mixin(PlayerManager.class)
abstract class MixinPlayerManager {
	@Inject(method = "onPlayerConnect", at = @At("RETURN"))
	private void hardcover$unlockAllRecipes(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
		if (Hardcover.CONFIG.unlockAllRecipes) {
			player.unlockRecipes(player.server.getRecipeManager().values());
		}
	}
}
