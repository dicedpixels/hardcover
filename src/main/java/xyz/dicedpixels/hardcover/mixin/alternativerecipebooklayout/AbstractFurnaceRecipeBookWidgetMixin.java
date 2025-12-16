package xyz.dicedpixels.hardcover.mixin.alternativerecipebooklayout;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.recipebook.FurnaceRecipeBookWidget;

import xyz.dicedpixels.hardcover.config.Configs;
import xyz.dicedpixels.hardcover.gui.Textures;

@Mixin(FurnaceRecipeBookWidget.class)
abstract class AbstractFurnaceRecipeBookWidgetMixin {
    @ModifyReturnValue(method = "getBookButtonTextures", at = @At("RETURN"))
    private ButtonTextures hardcover$setFurnaceFilterTexture(ButtonTextures textures) {
        if (Configs.alternativeRecipeBookLayout.getValue()) {
            return Textures.FURNACE_FILTER.asButtonTextures();
        }

        return textures;
    }
}
