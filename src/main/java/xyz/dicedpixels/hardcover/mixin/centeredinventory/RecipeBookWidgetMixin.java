package xyz.dicedpixels.hardcover.mixin.centeredinventory;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;

import xyz.dicedpixels.hardcover.config.Configs;

@Mixin(RecipeBookWidget.class)
abstract class RecipeBookWidgetMixin {
    @Unique
    private static final int RECIPE_BOOK_RIGHT_SPACING = 4;

    @Unique
    private static final int RECIPE_BOOK_TEXTURE_WIDTH = 147;

    @ModifyReturnValue(method = "getLeft", at = @At("RETURN"))
    private int hardcover$calculateLeft(int original) {
        if (Configs.centeredInventory.getValue()) {
            return original - (RECIPE_BOOK_TEXTURE_WIDTH / 2) - RECIPE_BOOK_RIGHT_SPACING;
        }

        return original;
    }

    @ModifyReturnValue(method = "findLeftEdge", at = @At("RETURN"))
    private int hardcover$recalculateLeftEdge(int original, int width, int backgroundWidth) {
        if (Configs.centeredInventory.getValue()) {
            return (width - backgroundWidth) / 2;
        }

        return original;
    }

    @ModifyVariable(method = "refreshTabButtons", at = @At("STORE"), ordinal = 0)
    private int hardcover$setLeft(int value) {
        if (Configs.centeredInventory.getValue()) {
            return value - (RECIPE_BOOK_TEXTURE_WIDTH / 2) - RECIPE_BOOK_RIGHT_SPACING;
        }

        return value;
    }
}
