package xyz.dicedpixels.hardcover.mixin.alternativerecipebooklayout;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.recipebook.AbstractFurnaceRecipeBookWidget;

import xyz.dicedpixels.hardcover.TextureProvider;
import xyz.dicedpixels.hardcover.config.Configs;

@Mixin(AbstractFurnaceRecipeBookWidget.class)
abstract class AbstractFurnaceRecipeBookWidgetMixin {
    @Unique
    private static final ButtonTextures CRAFTABLE_BUTTON_TEXTURES = new ButtonTextures(
        TextureProvider.FURNACE_FILTER_ENABLED.getTexture(),
        TextureProvider.FURNACE_FILTER.getTexture(),
        TextureProvider.FURNACE_FILTER_ENABLED.getSelectedTexture(),
        TextureProvider.FURNACE_FILTER.getSelectedTexture()
    );

    @ModifyArg(method = "setBookButtonTexture", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ToggleButtonWidget;setTextures(Lnet/minecraft/client/gui/screen/ButtonTextures;)V"), index = 0)
    private ButtonTextures hardcover$setCraftableButtonTextures(ButtonTextures textures) {
        if (Configs.alternativeRecipeBookLayout.getValue()) {
            return CRAFTABLE_BUTTON_TEXTURES;
        }

        return textures;
    }
}
