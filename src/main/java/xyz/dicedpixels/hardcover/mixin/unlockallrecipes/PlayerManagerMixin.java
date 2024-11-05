package xyz.dicedpixels.hardcover.mixin.unlockallrecipes;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ConnectedClientData;
import net.minecraft.server.network.ServerPlayerEntity;

import xyz.dicedpixels.hardcover.Config;

@Mixin(PlayerManager.class)
abstract class PlayerManagerMixin {
    @Inject(method = "onPlayerConnect", at = @At("RETURN"))
    private void hardcover$unlockAllRecipes(ClientConnection connection, ServerPlayerEntity player, ConnectedClientData clientData, CallbackInfo callbackInfo) {
        if (Config.instance().unlockAllRecipes) {
            player.unlockRecipes(player.server.getRecipeManager().values());
        }
    }
}
