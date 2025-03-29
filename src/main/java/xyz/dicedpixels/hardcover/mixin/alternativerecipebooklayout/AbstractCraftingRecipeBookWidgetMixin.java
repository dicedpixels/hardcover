package xyz.dicedpixels.hardcover.mixin.alternativerecipebooklayout;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.recipebook.AbstractCraftingRecipeBookWidget;

import xyz.dicedpixels.hardcover.TextureProvider;
import xyz.dicedpixels.hardcover.config.Configs;

@Mixin(AbstractCraftingRecipeBookWidget.class)
abstract class AbstractCraftingRecipeBookWidgetMixin {
    @Unique
    private static final ButtonTextures CRAFTABLE_BUTTON_TEXTURES = new ButtonTextures(
        TextureProvider.CRAFTING_FILTER_ENABLED.getTexture(),
        TextureProvider.CRAFTING_FILTER.getTexture(),
        TextureProvider.CRAFTING_FILTER_ENABLED.getSelectedTexture(),
        TextureProvider.CRAFTING_FILTER.getSelectedTexture()
    );

    @ModifyArg(method = "setBookButtonTexture", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ToggleButtonWidget;setTextures(Lnet/minecraft/client/gui/screen/ButtonTextures;" + ")V"), index = 0)
    private ButtonTextures hardcover$setCraftableButtonTextures(ButtonTextures textures) {
        if (Configs.alternativeRecipeBookLayout.getValue()) {
            return CRAFTABLE_BUTTON_TEXTURES;
        }

        return textures;
    }
}
