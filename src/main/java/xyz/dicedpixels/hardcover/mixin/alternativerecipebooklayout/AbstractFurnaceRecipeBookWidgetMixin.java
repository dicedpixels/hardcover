package xyz.dicedpixels.hardcover.mixin.alternativerecipebooklayout;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.recipebook.AbstractFurnaceRecipeBookWidget;

import xyz.dicedpixels.hardcover.config.Configs;
import xyz.dicedpixels.hardcover.gui.Textures;

@Mixin(AbstractFurnaceRecipeBookWidget.class)
abstract class AbstractFurnaceRecipeBookWidgetMixin {
    @ModifyArg(method = "setBookButtonTexture", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ToggleButtonWidget;setTextures(Lnet/minecraft/client/gui/screen/ButtonTextures;)V"), index = 0)
    private ButtonTextures hardcover$setFurnaceFilterTexture(ButtonTextures textures) {
        if (Configs.alternativeRecipeBookLayout.getValue()) {
            return Textures.FURNACE_FILTER.asButtonTextures();
        }

        return textures;
    }
}
