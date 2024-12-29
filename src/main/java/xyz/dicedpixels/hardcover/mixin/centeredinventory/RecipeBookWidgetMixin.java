package xyz.dicedpixels.hardcover.mixin.centeredinventory;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;

import xyz.dicedpixels.hardcover.Config;

@Debug(export = true)
@Mixin(RecipeBookWidget.class)
abstract class RecipeBookWidgetMixin {
    @ModifyReturnValue(method = "getLeft", at = @At("RETURN"))
    private int hardcover$calculateLeft(int original) {
        return Config.instance().centeredInventory ? (original - 147 / 2) - 4 : original;
    }

    @ModifyReturnValue(method = "findLeftEdge", at = @At("RETURN"))
    private int hardcover$recalculateLeftEdge(int original, int width, int backgroundWidth) {
        return Config.instance().centeredInventory ? (width - backgroundWidth) / 2 : original;
    }

    @ModifyVariable(method = "refreshTabButtons", at = @At("STORE"), ordinal = 0)
    private int hardcover$setLeft(int value) {
        return Config.instance().centeredInventory ? value - 147 / 2 - 4 : value;
    }
}
