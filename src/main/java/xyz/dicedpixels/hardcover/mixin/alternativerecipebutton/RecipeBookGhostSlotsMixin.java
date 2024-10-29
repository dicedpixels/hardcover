package xyz.dicedpixels.hardcover.mixin.alternativerecipebutton;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.recipebook.RecipeBookGhostSlots;
import net.minecraft.client.render.RenderLayer;

import xyz.dicedpixels.hardcover.Config;

@Mixin(RecipeBookGhostSlots.class)
abstract class RecipeBookGhostSlotsMixin {
    @WrapOperation(method = "draw", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(Lnet/minecraft/client/render/RenderLayer;IIIII)V"))
    private void hardcover$disableGhostSlotOverlay(DrawContext instance, RenderLayer layer, int x1, int y1, int x2, int y2, int color, Operation<Void> original, DrawContext context, MinecraftClient client, int x, int y, boolean notInventory, @Local(ordinal = 2) int slotIndex) {
        if (Config.instance().alternativeRecipeButton) {
            if (slotIndex == 0 && notInventory) {
                context.fill(x1 - 4, y1 - 4, x2 + 4, y2 + 4, 200, 0x30FFFFFF);
            } else {
                context.fill(x1, y1, x2, y2, 200, 0x30FFFFFF);
            }
        } else {
            original.call(context, layer, x1, y1, x2, y2, color);
        }
    }
}
