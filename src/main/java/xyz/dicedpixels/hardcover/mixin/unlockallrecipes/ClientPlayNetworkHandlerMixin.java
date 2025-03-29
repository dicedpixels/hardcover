package xyz.dicedpixels.hardcover.mixin.unlockallrecipes;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.network.ClientPlayNetworkHandler;

import xyz.dicedpixels.hardcover.config.Configs;

@Mixin(ClientPlayNetworkHandler.class)
abstract class ClientPlayNetworkHandlerMixin {
    @ModifyExpressionValue(method = "onRecipeBookAdd", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/packet/s2c/play/RecipeBookAddS2CPacket$Entry;shouldShowNotification()Z"))
    private boolean hardcover$hideRecipeToastsAndStopSound(boolean original) {
        if (Configs.unlockAllRecipes.getValue()) {
            return false;
        }

        return original;
    }
}
