package xyz.dicedpixels.hardcover.mixin.quickcraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;

import xyz.dicedpixels.hardcover.config.Configs;
import xyz.dicedpixels.hardcover.feature.QuickCraft;

@Mixin(ClientPlayNetworkHandler.class)
abstract class ClientPlayNetworkHandlerMixin {
    @Inject(method = "onScreenHandlerSlotUpdate", at = @At("TAIL"))
    private void hardcover$scheduleQuickCraftOnSlotUpdate(ScreenHandlerSlotUpdateS2CPacket packet, CallbackInfo callbackInfo) {
        if (Configs.quickCraft.getValue()) {
            if (QuickCraft.hasKeyDown()) {
                if (packet.getSlot() == 0 && packet.getStack() != null) {
                    QuickCraft.setScheduled(true);
                }
            }
        }
    }
}
