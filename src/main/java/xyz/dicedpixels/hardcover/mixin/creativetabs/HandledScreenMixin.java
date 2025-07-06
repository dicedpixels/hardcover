package xyz.dicedpixels.hardcover.mixin.creativetabs;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.ingame.HandledScreen;

import xyz.dicedpixels.hardcover.feature.CreativeTabs;

@Mixin(HandledScreen.class)
abstract class HandledScreenMixin {
    @Inject(method = "close", at = @At("TAIL"))
    private void hardcover$setCraftingScreen(CallbackInfo callbackInfo) {
        CreativeTabs.setCraftingScreen(false);
    }
}
