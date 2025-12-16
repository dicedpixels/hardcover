package xyz.dicedpixels.hardcover.mixin.alternativerecipebooklayout;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.recipebook.CraftingRecipeBookWidget;

import xyz.dicedpixels.hardcover.config.Configs;
import xyz.dicedpixels.hardcover.gui.Textures;

@Mixin(CraftingRecipeBookWidget.class)
abstract class AbstractCraftingRecipeBookWidgetMixin {
    @ModifyReturnValue(method = "getBookButtonTextures", at = @At("RETURN"))
    private ButtonTextures hardcover$setCraftingFilterTexture(ButtonTextures textures) {
        if (Configs.alternativeRecipeBookLayout.getValue()) {
            return Textures.CRAFTING_FILTER.asButtonTextures();
        }

        return textures;
    }
}
